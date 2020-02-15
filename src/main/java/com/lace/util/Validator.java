package com.lace.util;

import com.lace.exception.BadRequestException;
import com.lace.model.requests.LoginRequest;
import java.util.Objects;

/**
 *
 * @author hackdaemon
 */
public final class Validator {

  public static void validateLoginRequest(LoginRequest loginRequest) {
    if (loginRequest.getMobileNumber().length() != 11) {
     throw new BadRequestException("Mobile number must be 11 digits");
    }
    if (loginRequest.getPassword().length() < 8) {
      throw new BadRequestException("Password must be at least 8 characters");
    }
    if (Objects.isNull(loginRequest.getMobileNumber())) {
      throw new BadRequestException("Mobile number is required");
    }
    if (Objects.isNull(loginRequest.getPassword())) {
      throw new BadRequestException("Password is required");
    }
  }
}
