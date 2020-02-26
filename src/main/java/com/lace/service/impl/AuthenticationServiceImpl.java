package com.lace.service.impl;

import com.lace.exception.GenericException;
import com.lace.model.User;
import com.lace.model.UserInfo;
import com.lace.model.requests.SignupRequest;
import com.lace.model.response.GenericResponse;
import com.lace.repository.RoleRepository;
import com.lace.repository.UserRepository;
import com.lace.service.AuthenticationService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author hackdaemon
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  
  private static final String USER_ROLE = "USER_ROLE";
  
  @Autowired
  public AuthenticationServiceImpl(
    UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  @Override
  public GenericResponse doUserLogin() {
    return GenericResponse
            .builder()
            .status("success")
            .responseCode("00")
            .message("successful login")
            .build();
  }
  
  @Override
  public GenericResponse doUserSignup(List<Object> dataList) {
    String encodedPassword = null;
    SignupRequest signupRequest = (SignupRequest) dataList.get(0);
    String password = signupRequest.getPassword();
    String confirmPassword = signupRequest.getConfirmPassword();
    if ( ! password.equalsIgnoreCase(confirmPassword)) {
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
    user.setCreatedBy("admin");
    user.setUpdatedBy("admin");
    user.setRoles(Collections.singleton(roleRepository.findByRole(USER_ROLE)));
    user = userRepository.save(user);
    if (Objects.isNull(user)) {
      throw new GenericException("unble to save to DB");
    }
    return GenericResponse
            .builder()
            .status("success")
            .responseCode("00")
            .message("successful signup")
            .data(dataList)
            .build();
  }
}
