package com.lace.controller;

import com.lace.model.requests.LoginRequest;
import com.lace.model.response.GenericResponse;
import com.lace.util.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hackdaemon
 */
@RestController
@RequestMapping(path = "/api/v1", method = RequestMethod.POST)
public class AuthenticationController {

  @PostMapping("/login")
  public ResponseEntity<GenericResponse> authenticate(
    @RequestBody 
    LoginRequest loginRequest
  ) {
    Validator.validateLoginRequest(loginRequest);
    GenericResponse response;
    response = 
          GenericRespo
    return ResponseEntity.ok(response);
  }
}
