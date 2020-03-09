package com.lace.service.impl;

import com.lace.config.JwtTokenUtility;
import com.lace.constants.ApplicationConstants;
import com.lace.enums.AccountStatusEnum;
import com.lace.enums.AdminStatusEnum;
import com.lace.exception.GenericException;
import com.lace.exception.ResourceNotFoundException;
import com.lace.exception.UnauthorizedException;
import com.lace.model.AdminEntity;
import com.lace.model.PasswordResetToken;
import com.lace.model.Privilege;
import com.lace.model.Role;
import com.lace.model.User;
import com.lace.model.UserInfo;
import com.lace.model.UserVerificationToken;
import com.lace.model.requests.LoginRequest;
import com.lace.model.requests.SignupRequest;
import com.lace.model.response.AuthenticationResponse;
import com.lace.model.response.GenericResponse;
import com.lace.model.response.UserResponse;
import com.lace.repository.AdminEntityRepository;
import com.lace.repository.PasswordResetTokenRepository;
import com.lace.repository.UserRepository;
import com.lace.repository.UserVerificationRepository;
import com.lace.service.UserAuthService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author hackdaemon
 */
@Slf4j
@Service
public class UserAuthServiceImpl implements UserAuthService, ApplicationConstants {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtility jwtTokenUtil;
    
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    
    @Autowired
    private UserVerificationRepository userVerificationRepository;
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private MessageSource messages;
    
    @Autowired 
    private AdminEntityRepository adminEntityRepository;
    
    @Value("${support.email}")
    private String fromEmail;
    
    @Value("${email.smtp.username}")
    private String smtpUsername;
    
    @Value("${email.smtp.password}")
    private String smtpPassword;
    
    @Value("${email.smtp.host}")
    private String smtpHost;
    
    @Value("${email.smtp.port}")
    private Integer smtpPort;
    
    /**
     * 
     * @param token
     * @return com.lace.model.UserVerificationToken
     */
    @Override
    public UserVerificationToken getVerificationToken(final String token) {
        return userVerificationRepository.findByToken(token);
    }
    
    /**
     * 
     * @param verificationToken
     * @return com.lace.model.User
     */
    @Override
    public User getUserFromVerificationToken(final String verificationToken) {
        final UserVerificationToken token = userVerificationRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    /**
     *
     * @param signupRequest
     * @return GenericResponse
     */
    @Override
    public GenericResponse registerUser(SignupRequest signupRequest) {
        String encodedPassword = null;
        String password = signupRequest.getPassword();
        String confirmPassword = signupRequest.getConfirmPassword();
        if ( ! password.equals(confirmPassword)) {
            throw new GenericException("passwords do not match");
        }
        String email = signupRequest.getEmail();
        String mobileNumber = signupRequest.getMobileNumber();
        if (this.emailExists(email)) {
            throw new GenericException("email already taken");
        }
        if (this.mobileNumberExists(mobileNumber)) {
            throw new GenericException("mobile number already taken");
        }
        User user = new User();
        UserInfo userInfo = new UserInfo();
        userInfo.setGender(signupRequest.getGender());
        userInfo.setIsActive(Boolean.FALSE);
        user.setUserInfo(userInfo);
        user.setMobileNumber(signupRequest.getMobileNumber());
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(encodedPassword);
        user.setCreatedBy(SYSTEM_USER);
        user.setUpdatedBy(SYSTEM_USER);
        user = userRepository.save(user);
        // ***************************************************************
        // create user verification token
        // ***************************************************************
        String verificationToken = UUID.randomUUID().toString().replace("-", "");
        UserVerificationToken userToken = new UserVerificationToken();
        userToken.setToken(verificationToken);
        userToken.setUser(user);
        userVerificationRepository.save(userToken);
        if (Objects.isNull(user)) {
            throw new GenericException("unable to save to DB");
        }
        // send a confirmation email to user
        this.sendConfirmationEmail(user, request, verificationToken);
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
    
    private void sendConfirmationEmail(User user, HttpServletRequest request, String token) {
        try {
            HtmlEmail email = this.buildConfirmationEmail(
                this.getRequestURL(request), request.getLocale(),
                token, user
            );
            email.send();
        } catch (EmailException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * 
     * @param email
     * @param request
     * @return com.lace.model.response.PasswordResetResponse  
     */
    @Override
    public GenericResponse requestPasswordReset(String email, HttpServletRequest request) {
        User user = userRepository.findUserByEmail(email);
        GenericResponse response;
        if (user != null) {
            if (user.getUserInfo().getIsActive()) {
                try {
                    String token = UUID.randomUUID().toString();
                    this.createPasswordResetTokenForUser(user, token);
                    HtmlEmail htmlEmail = this.buildResetTokenEmail(
                            this.getRequestURL(request), request.getLocale(),
                            token, user
                    );
                    htmlEmail.send();
                } catch (EmailException ex) {
                    log.error(ex.getMessage());
                }
            } else {
                String errorMessage = "please confirm your account before resetting password";
                throw new GenericException(errorMessage);
            }  
        }
        response = GenericResponse
                    .builder()
                    .status("success")
                    .responseCode("00")
                    .message("mail successfully sent")
                    .build();
        response.setMessage("Mail has been sent to ");
        return response;
    }
    
    /**
     * 
     * @param request
     * @return java.lang.Stting
     */
    private String getRequestURL(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }
    
    /**
     * 
     * @param user
     * @param token 
     */
    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setUser(user);
        myToken.setToken(token);
        passwordResetTokenRepository.save(myToken);
    }
    
    /**
     * 
     * @param contextPath
     * @param locale
     * @param token
     * @param user
     * @return org.springframework.mail.SimpleMailMessage
     */
    private HtmlEmail buildResetTokenEmail(String contextPath, Locale locale, String token, User user) {
        String url; 
        url = String.format("%s/user/change/password/%s/token/%s", contextPath, user.getId(), token);
        String message = messages.getMessage("message.resetpassword", null, locale);
        return this.buildEmail("Reset Password", message + " \r\n" + url, user);
    }
    
    /**
     * 
     * @param subject
     * @param body
     * @param user
     * @return SimpleMailMessage
     */
    private HtmlEmail buildEmail(String subject, String body, User user) {
        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(smtpHost);
            email.setSmtpPort(smtpPort);
            email.setStartTLSEnabled(true);
            email.setAuthenticator(new DefaultAuthenticator(smtpUsername, smtpPassword));
            email.setSubject(subject);
            email.setHtmlMsg(body);
            email.addTo(user.getEmail());
            email.setFrom(fromEmail);
        } catch (EmailException ex) {
            log.error(ex.getMessage());
        }
        return email;
    }
    
    /**
     * 
     * @param appUrl
     * @param user
     * @param token
     * @param locale
     * @return HtmlEmail
     */
    private HtmlEmail buildConfirmationEmail(String appUrl, Locale locale, String token, User user) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = appUrl + "/signup/confirm/" + token + "/token";
        final String message = messages.getMessage("message.registrationsuccess", null, locale);
        final HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(smtpHost);
            email.setSmtpPort(smtpPort);
            email.setStartTLSEnabled(true);
            email.setAuthenticator(new DefaultAuthenticator(smtpUsername, smtpPassword));
            email.setFrom(fromEmail);
            email.setSubject(subject);
            email.setHtmlMsg(message);
            email.setMsg(message + " \r\n" + confirmationUrl);
            email.addTo(recipientAddress);
            email.send();
        } catch (EmailException ex) {
            log.error(ex.getMessage());
        }
        return email;
    }

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
        return this.buildGenericResponseFromUser(user);
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
        return this.buildGenericResponseFromUser(user);
    }

    /**
     *
     * @param loginRequest
     * @throws GenericException
     */
    @Override
    public void authenticate(LoginRequest loginRequest) throws GenericException {
        try {
            UsernamePasswordAuthenticationToken authToken;
            String mobileNumber = loginRequest.getMobileNumber();
            String password = loginRequest.getPassword();
            authToken = new UsernamePasswordAuthenticationToken(mobileNumber, password);
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
    public AuthenticationResponse buildAuthenticationResponse(UserDetails userDetails) {
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
        UserResponse userResponse = UserResponse.builder().build();
        authResponse.setUser(userResponse);
        return authResponse;
    }

    /**
     *
     * @param mobileNumber
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {
        User user = userRepository.findByMobileNumber(mobileNumber);
        if (user == null) {
            throw new UsernameNotFoundException(mobileNumber);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(1);
        user.getRoles().forEach((role) -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(
            user.getMobileNumber(), user.getPassword(), grantedAuthorities
        );
    }

    /**
     *
     * @param user
     * @return GenericResponse
     */
    private GenericResponse buildGenericResponseFromUser(User user) {
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
     * @param token
     * @return com.lace.model.PasswordResetToken
     */
    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordResetTokenRepository.findByToken(token);
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
            response = this.buildGenericResponseFromUser(user);
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
    
    /**
     * 
     * @param user
     * @param password 
     */
    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    /**
     * 
     * @param user
     * @param oldPassword
     * @return java.lang.Boolean
     */
    @Override
    public Boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
    
    @Override
    public UserVerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        UserVerificationToken userVerificationToken;
        userVerificationToken = userVerificationRepository.findByToken(existingVerificationToken);
        userVerificationToken.updateToken(UUID.randomUUID().toString().replace("-", ""));
        userVerificationToken = userVerificationRepository.save(userVerificationToken);
        return userVerificationToken;
    }
    
    /**
     * 
     * @param email
     * @return java.lang.Boolean
     */
    private Boolean emailExists(final String email) {
        return userRepository.findUserByEmail(email) != null;
    }
    
    /**
     * 
     * @param mobileNumber
     * @return java.lang.Boolean
     */
    private Boolean mobileNumberExists(final String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber) != null;
    }
    
    /**
     * 
     * @param token
     * @return java.lang.String
     */
    @Override
    public GenericResponse validateVerificationToken(String token) {
        final UserVerificationToken verificationToken = userVerificationRepository.findByToken(token);
        GenericResponse response;
        if (verificationToken == null) {
            response = GenericResponse
                        .builder()
                        .message(TOKEN_INVALID)
                        .responseCode("07")
                        .status("failed")
                        .build();
            return response;
        }
        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        long timeMillis = verificationToken.getExpiryDate().toEpochSecond(ZoneOffset.UTC);
        if ((timeMillis - cal.getTime().getTime()) <= 0) {
            userVerificationRepository.delete(verificationToken);
            response = GenericResponse
                        .builder()
                        .message(TOKEN_EXPIRED)
                        .responseCode("07")
                        .status("failed")
                        .build();
            return response;
        }
        user.getUserInfo().setIsActive(true);
        userVerificationRepository.delete(verificationToken);
        userRepository.save(user);
        response = GenericResponse
                        .builder()
                        .message(TOKEN_VALID)
                        .responseCode("00")
                        .status("success")
                        .build();
        return response;
    }
    
    /**
     * 
     * @param id
     * @param token
     * @return java.lang.String
     */
    @Override
    public String validatePasswordResetToken(long id, String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            return TOKEN_INVALID;
        }
        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().toEpochSecond(ZoneOffset.UTC) - cal.getTime().getTime()) <= 0) {
            return TOKEN_EXPIRED;
        }
        final User user = passToken.getUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(
            user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
    
    /**
     * 
     * @param roles
     * @return java.util.Collection
     */
    private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    /**
     * 
     * @param roles
     * @return java.util.List
     */
    private List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<>();
        final List<Privilege> collection = new ArrayList<>();
        roles.forEach((role) -> {
            collection.addAll(role.getPrivileges());
        });
        collection.forEach((item) -> {
            privileges.add(item.getName());
        });
        return privileges;
    }

    /**
     * 
     * @param privileges
     * @return java.util.List
     */
    private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        privileges.forEach((privilege) -> {
            authorities.add(new SimpleGrantedAuthority(privilege));
        });
        return authorities;
    }

    /**
     * 
     * @return java.lang.String
     */
    public String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
    
    /**
     *
     * @param userId
     * @param accountStatus
     * @param adminStatus
     * @return com.lace.model.response.GenericResponse
     */
    @Override
    public GenericResponse upgradeUserToAdmin(
        Long userId, AccountStatusEnum accountStatus, AdminStatusEnum adminStatus
    ) {
        GenericResponse response;
        Optional<User> userEntity = userRepository.findById(userId);
        if ( ! userEntity.isPresent()) {
            String errorMessage = String.format("user with id: %d not found", userId);
            throw new GenericException(errorMessage);
        }
        AdminEntity admin = adminEntityRepository.findByUser(userEntity.get());
        if (Objects.isNull(admin)) {
            admin = new AdminEntity();
            admin.setAccountStatus(AccountStatusEnum.ACTIVE);
            admin.setAdminStatus(AdminStatusEnum.ADMIN);
            admin.setUser(userEntity.get());
            admin = adminEntityRepository.save(admin);
            response = GenericResponse
                        .builder()
                        .status("success")
                        .responseCode("00")
                        .message("successfully upgraded user with id: " + userId + " to admin")
                        .build();
        } else {
            response = GenericResponse
                        .builder()
                        .status("failure")
                        .responseCode("07")
                        .message("user with id: " + userId + " is already an admin")
                        .build();
        }   
        return response;
    }

}
