package com.lace.controller;

import com.google.gson.GsonBuilder;
import com.lace.model.requests.LoginRequest;
import com.lace.model.requests.PasswordResetRequest;
import com.lace.model.requests.SignupRequest;
import com.lace.model.response.AuthenticationResponse;
import com.lace.model.response.GenericResponse;
import com.lace.model.response.PasswordResetResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lace.service.UserAuthService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author hackdaemon
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class UserController {

    private final UserAuthService authenticationService;

    @Autowired
    public UserController(UserAuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody @Valid LoginRequest loginRequest
    ) {
        log.info("HTTP Method: POST");
        log.info("========================== start api/v1/login ==============================");
        String mobileNumber = loginRequest.getMobileNumber();
        AuthenticationResponse authResponse = null;
        UserDetails userDetails = authenticationService.loadUserByUsername(mobileNumber);
        if (userDetails != null) {
            authenticationService.authenticate(loginRequest);
            authResponse = authenticationService.doBuildLoginResponse(userDetails);
        }
        log.info("========================== stop api/v1/login ==============================");
        return ResponseEntity.ok(authResponse);
    }
    
    @PostMapping("/password/reset")
    public ResponseEntity<PasswordResetResponse> resetPassword(
        @RequestBody @Valid PasswordResetRequest passwordResetRequest,
        HttpServletRequest request
    ) {
        log.info("HTTP Method: POST");
        log.info("========================== start api/v1/password/reset ==============================");
        String email = passwordResetRequest.getEmail();
        PasswordResetResponse requestPasswordReset = null;
        requestPasswordReset = authenticationService.requestPasswordReset(email, request);
        log.info("========================== stop api/v1/password/reset ==============================");
        return ResponseEntity.ok(requestPasswordReset);
    }

    @PostMapping("/signup")
    public ResponseEntity<GenericResponse> signup(
        @RequestBody @Valid SignupRequest signupRequest
    ) {
        log.info("HTTP Method: POST");
        log.info("========================== start api/v1/signup ==============================");
        GenericResponse response;
        response = authenticationService.doRegister(signupRequest);
        log.info("========================== stop api/v1/signup ==============================");
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> updateById(
        @PathVariable("id") Long id, @RequestBody @Valid SignupRequest user
    ) {
        log.info("HTTP Method: PUT");
        log.info("=============== start api/v1/user/{id} ===============");
        GenericResponse userResponse = authenticationService.update(id, user);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(userResponse);
        String jsonRequest = gsonBuilder.create().toJson(user);
        log.info("id=" + id);
        log.info("request=" + jsonRequest);
        log.info("response=" + jsonResponse);
        log.info("=============== stop api/v1/user/{id} ===============");
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> findUserById(@PathVariable("id") Long id) {
        log.info("HTTP Method: GET");
        log.info("=============== start api/v1/user/{id} ===============");
        GenericResponse userResponse = authenticationService.findById(id);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(userResponse);
        log.info("id=" + id);
        log.info("response=" + jsonResponse);
        log.info("=============== stop api/v1/user/{id} ===============");
        return ResponseEntity.ok(userResponse);
    }

//    @GetMapping
//    public ResponseEntity<GenericResponse> findUserByEmail(
//        @RequestParam(value = "email") String email
//    ) {
//        log.info("HTTP Method: GET");
//        log.info("=============== start api/v1/user?email=email ===============");
//        GenericResponse userResponse = authenticationService.findByEmail(email);
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        String jsonResponse = gsonBuilder.create().toJson(userResponse);
//        log.info("response=" + jsonResponse);
//        log.info("=============== stop api/v1/user?email=email ===============");
//        return ResponseEntity.ok(userResponse);
//    }
    
    @GetMapping
    public ResponseEntity<GenericResponse> findAll(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        log.info("HTTP Method: GET");
        log.info("=============== start api/v1/user?page=1&size=10 ===============");
        GenericResponse userResponseList = authenticationService.findAll(page, size);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(userResponseList);
        log.info("response=" + jsonResponse);
        log.info("=============== stop api/v1/user?page=1&size=10 ===============");
        return ResponseEntity.ok(userResponseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        log.info("HTTP Method: DELETE");
        log.info("=============== start api/v1/user/{id} ===============");
        authenticationService.deleteById(id);
        log.info("=============== start api/v1/user/{id} ===============");
        return ResponseEntity.noContent().build();
    }
}
