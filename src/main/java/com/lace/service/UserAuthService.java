package com.lace.service;

import com.lace.exception.GenericException;
import com.lace.exception.ResourceNotFoundException;
import com.lace.model.User;
import com.lace.model.requests.LoginRequest;
import com.lace.model.requests.SignupRequest;
import com.lace.model.response.AuthenticationResponse;
import com.lace.model.response.GenericResponse;
import com.lace.model.response.PasswordResetResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author hackdaemon
 */
public interface UserAuthService extends UserDetailsService {

    GenericResponse doRegister(SignupRequest signupRequest);
    GenericResponse findAll(int page, int size) throws GenericException;
    PasswordResetResponse requestPasswordReset(String email, HttpServletRequest request);
    GenericResponse findByEmail(String email) throws ResourceNotFoundException;
    GenericResponse findById(Long id) throws ResourceNotFoundException;
    void authenticate(LoginRequest loginRequest) throws GenericException;
    AuthenticationResponse doBuildLoginResponse(UserDetails userDetails);
    GenericResponse update(Long id, SignupRequest user);
    void deleteById(Long id);
    void createPasswordResetTokenForUser(User user, String token);
}
