package com.lace.service.impl;

import com.lace.config.JwtTokenUtil;
import com.lace.constants.ApplicationConstants;
import com.lace.exception.GenericException;
import com.lace.exception.ResourceNotFoundException;
import com.lace.exception.UnauthorizedException;
import com.lace.model.PasswordResetToken;
import com.lace.model.User;
import com.lace.model.UserInfo;
import com.lace.model.requests.LoginRequest;
import com.lace.model.requests.SignupRequest;
import com.lace.model.response.AuthenticationResponse;
import com.lace.model.response.GenericResponse;
import com.lace.model.response.PasswordResetResponse;
import com.lace.model.response.UserResponse;
import com.lace.repository.PasswordResetTokenRepository;
import com.lace.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lace.service.UserAuthService;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

/**
 *
 * @author hackdaemon
 */
@Service
@Slf4j
public class UserAuthServiceImpl implements UserAuthService, ApplicationConstants {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    /**
     *
     * @param signupRequest
     * @return GenericResponse
     */
    @Override
    public GenericResponse doRegister(SignupRequest signupRequest) {
        String encodedPassword = null;
        String password = signupRequest.getPassword();
        String confirmPassword = signupRequest.getConfirmPassword();
        if (!password.equalsIgnoreCase(confirmPassword)) {
            throw new GenericException("passwords do not match");
        }
        User user = new User();
        UserInfo userInfo = new UserInfo();
        userInfo.setGender(signupRequest.getGender());
        user.setUserInfo(userInfo);
        user.setMobileNumber(signupRequest.getMobileNumber());
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(encodedPassword);
        user.setCreatedBy(DEFAULT_ADMIN_USER);
        user.setUpdatedBy(DEFAULT_ADMIN_USER);
        user = userRepository.save(user);
        if (Objects.isNull(user)) {
            throw new GenericException("unble to save to DB");
        }
        List<User> userList = new ArrayList<>(INIT);
        User userData = User.builder()
                            .userInfo(userInfo)
                            .email(signupRequest.getEmail())
                            .firstName(signupRequest.getFirstName())
                            .lastName(signupRequest.getLastName())
                            .mobileNumber(signupRequest.getMobileNumber())
                            .build();
        userData.setId(user.getId());
        userList.add(userData);
        return GenericResponse
                    .builder()
                    .status("success")
                    .responseCode("00")
                    .message("successful signup")
                    .userData(userList)
                    .build();
    }

    /**
     *
     * @param page
     * @param size
     * @return GenericResponse
     * @throws GenericException
     */
    @Override
    public GenericResponse findAll(int page, int size) throws GenericException {
        GenericResponse response = null;
        Pageable paging = PageRequest.of(page, size);
        Page<User> pagedResult = userRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            List<User> userList = pagedResult.getContent();
            response = GenericResponse
                        .builder()
                        .responseCode("00")
                        .status("success")
                        .rowCount(userRepository.count())
                        .perPage(Long.valueOf(size))
                        .currentPage(Long.valueOf(page))
                        .userData(userList)
                        .build();
        } else {
            response = GenericResponse
                            .builder()
                            .responseCode("00")
                            .status("success")
                            .rowCount(userRepository.count())
                            .perPage(Long.valueOf(size))
                            .currentPage(Long.valueOf(page))
                            .userData(null)
                            .build();
        }
        return response;
    }

    @Override
    public PasswordResetResponse requestPasswordReset(String email, HttpServletRequest request) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            // todo
        } else {
            String token = UUID.randomUUID().toString();
            this.createPasswordResetTokenForUser(user, token);
            // mailSender.send(this.buildResetTokenEmail(this.getRequestURL(request), request.getLocale(), token, user));
        }
        return new PasswordResetResponse();
    }
    
    private String getRequestURL(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }
    
    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken 
             = PasswordResetToken
                .builder()
                .user(user)
                .token(token)
                .build();
        passwordResetTokenRepository.save(myToken);
    }
    
//    private SimpleMailMessage buildResetTokenEmail(String contextPath, Locale locale, String token, User user) {
//        String url = contextPath + "/user/change/password?id=" + user.getId() + "&token=" + token;
//        String message = messages.getMessage("message.resetPassword", null, locale);
//        return this.buildEmail("Reset Password", message + " \r\n" + url, user);
//    }

    /**
     *
     * @param email
     * @return GenericResponse
     * @throws ResourceNotFoundException
     */
    @Override
    public GenericResponse findByEmail(String email) throws ResourceNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException("Cannot find user with email: " + email);
        }
        return this.doBuildGenericResponseFromUser(user);
    }

    /**
     *
     * @param id
     * @return GenericResponse
     * @throws ResourceNotFoundException
     */
    @Override
    public GenericResponse findById(Long id) throws ResourceNotFoundException {
        User user = null;
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            throw new ResourceNotFoundException("Cannot find user with id: " + id);
        }
        return this.doBuildGenericResponseFromUser(user);
    }

    /**
     *
     * @param loginRequest
     * @throws GenericException
     */
    @Override
    public void authenticate(LoginRequest loginRequest) throws GenericException {
        try {
            String mobileNumber = loginRequest.getMobileNumber();
            String password = loginRequest.getPassword();
            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(mobileNumber, password);
            Authentication authentication = authenticationManager.authenticate(authToken);
            if (authentication == null) {
                throw new UnauthorizedException("invalid user credentials");
            }
            User user = userRepository.findByMobileNumber(mobileNumber);
            if (Objects.nonNull(user)) {
                LocalDateTime lastLoginTime = user.getLastLoginTime();
                if (Objects.isNull(lastLoginTime)) { // first time login
                    user.setLastLoginTime(LocalDateTime.now());
                    userRepository.save(user);
                }
            }
        } catch (DisabledException exception) {
            throw new UnauthorizedException("user account is disabled", exception);
        } catch (BadCredentialsException exception) {
            throw new UnauthorizedException("invalid user credentials", exception);
        } catch (GenericException exception) {
            log.error(exception.getMessage());
        }
    }

    /**
     *
     * @param userDetails
     * @return AuthenticationResponse
     */
    @Override
    public AuthenticationResponse doBuildLoginResponse(UserDetails userDetails) {
        String token = jwtTokenUtil.generateToken(userDetails);
        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setJwtToken(jwtTokenUtil.generateToken(userDetails));
        Date expirationFromToken = jwtTokenUtil.getExpirationDateFromToken(token);
        Date issuedAtDate = jwtTokenUtil.getIssuedAtDateFromToken(token);
        LocalDateTime expiresAt = LocalDateTime.parse(expirationFromToken.toString());
        LocalDateTime issuedAt = LocalDateTime.parse(issuedAtDate.toString());
        authResponse.setExpiresAt(expiresAt);
        authResponse.setIssuedAt(issuedAt);
        authResponse.setIssuer("lace");
        return authResponse;
    }

    /**
     *
     * @param mobileNumber
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {
        User user = userRepository.findByMobileNumber(mobileNumber);
        if (user == null) {
            throw new UsernameNotFoundException(mobileNumber);
        }
        return new org.springframework.security.core.userdetails.User(
            user.getMobileNumber(), user.getPassword(), new ArrayList<>()
        );
    }

    /**
     *
     * @param user
     * @return GenericResponse
     */
    private GenericResponse doBuildGenericResponseFromUser(User user) {
        UserResponse userResponse
                = UserResponse
                        .builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .mobileNumber(user.getMobileNumber())
                        .build();
        List<Object> userList = new ArrayList<>(1);
        userList.add(userResponse);
        GenericResponse response
                = GenericResponse
                        .builder()
                        .responseCode("00")
                        .status("success")
                        .payload(userList)
                        .build();
        return response;
    }

    /**
     *
     * @param id
     * @param signupRequest
     * @return GenericResponse
     */
    @Override
    public GenericResponse update(Long id, SignupRequest signupRequest) {
        User user = userRepository.findByMobileNumber(signupRequest.getMobileNumber());
        GenericResponse response = null;
        String mobile = signupRequest.getMobileNumber();
        if (Objects.nonNull(user)) {
            response = this.doBuildGenericResponseFromUser(user);
        } else {
            String errorMessage = String.format("User with mobile: %s cannot be found", mobile);
            throw new ResourceNotFoundException(errorMessage);
        }
        return response;
    }

    /**
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new ResourceNotFoundException("Cannot find user with id: " + id);
        }
        int deleteStatus = userRepository.deleteUserById(id);
        if (deleteStatus != 1) {
            throw new GenericException("Cannot delete user with id: " + id);
        } else {
            log.info("User resource with id: " + id + " has been deleted...");
        }
    }
}
