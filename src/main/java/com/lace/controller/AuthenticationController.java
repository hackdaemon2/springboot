package com.lace.controller;

import com.lace.model.requests.LoginRequest;
import com.lace.model.requests.SignupRequest;
import com.lace.model.response.GenericResponse;
import com.lace.service.AuthenticationService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hackdaemon
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class AuthenticationController {
  
  private final AuthenticationService authenticationService;
  
  @Autowired
  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/login")
  public ResponseEntity<GenericResponse> authenticate(
    @RequestBody 
    @Valid
    LoginRequest loginRequest
  ) {
    log.info("========================== start api/v1/login ==============================");
    GenericResponse response;
    response = authenticationService.doUserLogin();
    log.info("========================== stop api/v1/login ==============================");
    return ResponseEntity.ok(response);
  }
  
  @PostMapping("/signup")
  public ResponseEntity<GenericResponse> signup(
    @RequestBody 
    @Valid
    SignupRequest signupRequest
  ) {
    log.info("========================== start api/v1/signup ==============================");
    GenericResponse response;
    List<Object> dataList = new ArrayList<>();
    dataList.add(signupRequest);
    response = authenticationService.doUserSignup(dataList);
    log.info("========================== stop api/v1/signup ==============================");
    return ResponseEntity.ok(response);
  }
}
