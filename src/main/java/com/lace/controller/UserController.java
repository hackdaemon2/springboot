package com.lace.controller;

import com.google.gson.GsonBuilder;
import com.lace.model.requests.ChangePasswordRequest;
import com.lace.model.requests.LoginRequest;
import com.lace.model.requests.PasswordResetRequest;
import com.lace.model.requests.SignupRequest;
import com.lace.model.response.AuthenticationResponse;
import com.lace.model.response.GenericResponse;
import com.lace.service.UserAuthService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hackdaemon
 */
@Slf4j
@RestController
@RequestMapping(
    path = "/api/v1/user", 
    consumes = { MediaType.APPLICATION_JSON_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE }
)
public class UserController {
    
    private final UserAuthService authenticationService;

    /**
     * 
     * @param authenticationService 
     */
    @Autowired
    public UserController(UserAuthService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    /**
     * 
     * @param token
     * @return org.springframework.http.ResponseEntity
     */
    @PostMapping(path = "/signup/confirm/{token}/token")
    public ResponseEntity<?> confirmUserRegistration(
        @PathVariable("token") String token
    ) {
        log.info("HTTP Method: POST");
        log.info("========== start api/v1/user/signup/confirm/{token}/token ==============");
        GenericResponse response = authenticationService.validateVerificationToken(token);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(response);
        log.info("token=" + token);
        log.info("response=" + jsonResponse);
        log.info("========== stop api/v1/user/signup/confirm/{token}/token ==============");
        return ResponseEntity.ok(response);
    }

    /**
     * 
     * @param loginRequest
     * @return org.springframework.http.ResponseEntity
     */
    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody @Valid LoginRequest loginRequest
    ) {
        log.info("HTTP Method: POST");
        log.info("==================== start api/v1/user/login ========================");
        String mobileNumber = loginRequest.getMobileNumber();
        AuthenticationResponse authResponse = null;
        UserDetails userDetails = authenticationService.loadUserByUsername(mobileNumber);
        if (userDetails != null) {
            authenticationService.authenticate(loginRequest);
            authResponse = authenticationService.buildAuthenticationResponse(userDetails);
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(authResponse);
        StringBuilder msg = new StringBuilder(); 
        msg.append("request={\"mobile_number\": ");
        msg.append(loginRequest.getMobileNumber());
        msg.append(", ");
        msg.append("\"password\": ");
        msg.append("******* }");
        log.info(msg.toString());
        log.info("response=" + jsonResponse);
        log.info("==================== stop api/v1/user/login ========================");
        return ResponseEntity.ok(authResponse);
    }
    
    /**
     * 
     * @param passwordResetRequest
     * @param request
     * @return org.springframework.http.ResponseEntity
     */
    @PostMapping(path = "/password/reset")
    public ResponseEntity<GenericResponse> resetPassword(
        @RequestBody @Valid PasswordResetRequest passwordResetRequest,
        HttpServletRequest request
    ) {
        log.info("HTTP Method: POST");
        log.info("==================== start api/v1/user/password/reset ========================");
        String email = passwordResetRequest.getEmail();
        GenericResponse response = null;
        response = authenticationService.requestPasswordReset(email, request);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(response);
        String jsonRequest = gsonBuilder.create().toJson(passwordResetRequest);
        log.info("request=" + jsonRequest);
        log.info("response=" + jsonResponse);
        log.info("==================== stop api/v1/user/password/reset ========================");
        return ResponseEntity.ok(response);
    }
    
    /**
     * 
     * @param changePasswordRequest
     * @param request
     * @return org.springframework.http.ResponseEntity
     */
    @PostMapping(path = "/password/change")
    public ResponseEntity<GenericResponse> changePassword(
        @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
        HttpServletRequest request
    ) {
        log.info("HTTP Method: POST");
        log.info("==================== start api/v1/user/password/change ========================");
        GenericResponse response = GenericResponse.builder().build();
        log.info("==================== stop api/v1/user/password/change ========================");
        return ResponseEntity.ok(response);
    }

    /**
     * 
     * @param signupRequest
     * @return org.springframework.http.ResponseEntity
     */
    @PostMapping(path = "/signup")
    public ResponseEntity<GenericResponse> signup(
        @RequestBody @Valid SignupRequest signupRequest
    ) {
        log.info("HTTP Method: POST");
        log.info("==================== start api/v1/user/signup ========================");
        GenericResponse response;
        response = authenticationService.registerUser(signupRequest);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(response);
        String jsonRequest = gsonBuilder.create().toJson(signupRequest);
        log.info("request=" + jsonRequest);
        log.info("response=" + jsonResponse);
        log.info("==================== stop api/v1/user/signup ========================");
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    /**
     * 
     * @param id
     * @param user
     * @return org.springframework.http.ResponseEntity
     */
    @PutMapping(path = "/{id}")
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

    /**
     * 
     * @param id
     * @return org.springframework.http.ResponseEntity
     */
    @GetMapping(path = "/{id}")
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

    /**
     * 
     * @param email
     * @return org.springframework.http.ResponseEntity
     */
    @GetMapping(path = "/user")
    public ResponseEntity<GenericResponse> getUserByEmail(
        @RequestParam(name = "email", required = true) String email
    ) {
        log.info("HTTP Method: GET");
        log.info("=============== start api/v1/user?email=email ===============");
        GenericResponse userResponse = authenticationService.findByEmail(email);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(userResponse);
        log.info("response=" + jsonResponse);
        log.info("=============== stop api/v1/user?email=email ===============");
        return ResponseEntity.ok(userResponse);
    }
    
    /**
     * 
     * @param page
     * @param size
     * @return org.springframework.http.ResponseEntity
     */
    @GetMapping
    public ResponseEntity<GenericResponse> findAll(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        log.info("HTTP Method: GET");
        log.info("=============== start api/v1/user?page={page}&size={size} ===============");
        GenericResponse userResponseList = authenticationService.findAll(page, size);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(userResponseList);
        log.info("response=" + jsonResponse);
        log.info("=============== stop api/v1/user?page=[page}&size={size} ===============");
        return ResponseEntity.ok(userResponseList);
    }

    /**
     * 
     * @param id
     * @return org.springframework.http.ResponseEntity
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        log.info("HTTP Method: DELETE");
        log.info("=============== start api/v1/user/{id} ===============");
        authenticationService.deleteById(id);
        log.info("=============== start api/v1/user/{id} ===============");
        return ResponseEntity.noContent().build();
    }
}
