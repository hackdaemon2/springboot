package com.lace.util;

import com.lace.exception.BadRequestException;
import com.lace.model.requests.LoginRequest;
import com.lace.model.requests.SignupRequest;
import java.util.Objects;

/**
 *
 * @author hackdaemon
 */
public final class Validator {

  public static void validateLoginRequest(LoginRequest loginRequest) {
    if (Objects.isNull(loginRequest.getMobileNumber())) {
      throw new BadRequestException("Mobile number is required");
    }
    if (Objects.isNull(loginRequest.getPassword())) {
      throw new BadRequestException("Password is required");
    }
  }
  
   public static void validateSignupRequest(SignupRequest signupRequest) {
    if (Objects.isNull(signupRequest.getMobileNumber())) {
      throw new BadRequestException("Mobile number is required");
    }
    if (Objects.isNull(signupRequest.getPassword())) {
      throw new BadRequestException("Password is required");
    }
    if (Objects.isNull(signupRequest.getConfirmPassword())) {
      throw new BadRequestException("Confirm password is required");
    }
    if (Objects.isNull(signupRequest.getFirstName())) {
      throw new BadRequestException("First name is required");
    }
    if (Objects.isNull(signupRequest.getLastName())) {
      throw new BadRequestException("Last name is required");
    }
    if (Objects.isNull(signupRequest.getEmail())) {
      throw new BadRequestException("Email is required");
    }
    if (signupRequest.getMobileNumber().length() != 11) {
     throw new BadRequestException("Mobile number must be 11 digits");
    }
    if (signupRequest.getPassword().length() < 8) {
      throw new BadRequestException("Password must be at least 8 characters");
    }
    if ( ! signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
      throw new BadRequestException("Passwords do not match");
    }
  }
}
