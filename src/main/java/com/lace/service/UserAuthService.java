package com.lace.service;

import com.lace.enums.AccountStatusEnum;
import com.lace.enums.AdminStatusEnum;
import com.lace.exception.GenericException;
import com.lace.exception.ResourceNotFoundException;
import com.lace.model.PasswordResetToken;
import com.lace.model.User;
import com.lace.model.UserVerificationToken;
import com.lace.model.requests.LoginRequest;
import com.lace.model.requests.SignupRequest;
import com.lace.model.response.AuthenticationResponse;
import com.lace.model.response.GenericResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author hackdaemon
 */
public interface UserAuthService extends UserDetailsService {

    GenericResponse registerUser(SignupRequest signupRequest);
    GenericResponse findAll(int page, int size) throws GenericException;
    GenericResponse requestPasswordReset(String email, HttpServletRequest request);
    GenericResponse findByEmail(String email) throws ResourceNotFoundException;
    GenericResponse findById(Long id) throws ResourceNotFoundException;
    void authenticate(LoginRequest loginRequest) throws GenericException;
    AuthenticationResponse buildAuthenticationResponse(UserDetails userDetails);
    GenericResponse update(Long id, SignupRequest user);
    void deleteById(Long id);
    void createPasswordResetTokenForUser(User user, String token);
    Boolean checkIfValidOldPassword(final User user, final String oldPassword);
    void changeUserPassword(final User user, final String password);
    User getUserFromVerificationToken(final String verificationToken);
    UserVerificationToken getVerificationToken(final String token);
    GenericResponse validateVerificationToken(String token);
    PasswordResetToken getPasswordResetToken(final String token);
    UserVerificationToken generateNewVerificationToken(final String existingVerificationToken);
    String validatePasswordResetToken(long id, String token);
    GenericResponse upgradeUserToAdmin(Long userId, AccountStatusEnum accountStatus, AdminStatusEnum adminStatus);
}
