package com.lace.controller;

import com.google.gson.GsonBuilder;
import com.lace.enums.AccountStatusEnum;
import com.lace.enums.AdminStatusEnum;
import com.lace.model.requests.UserUpgradeRequest;
import com.lace.model.response.GenericResponse;
import com.lace.service.UserAuthService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping(
    path = "/api/v1/admin", 
    consumes = {
        MediaType.APPLICATION_JSON_VALUE
    }, 
    produces = {
        MediaType.APPLICATION_JSON_VALUE
    }
)
public class AdminController {
    
    private final UserAuthService authenticationService;

    /**
     * 
     * @param authenticationService 
     */
    @Autowired
    public AdminController(UserAuthService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    @PostMapping(path = "/upgrade-user-status")
    public ResponseEntity<?> upgradeUserToAdmin(
        @Valid @RequestBody UserUpgradeRequest request
    ) {
        log.info("HTTP Method: POST");
        log.info("==================== start api/v1/admin/upgrade-user-status ========================");
        Long userId = request.getId();
        AccountStatusEnum accountStatus = request.getAccountStatusEnum();
        AdminStatusEnum adminStatus = request.getAdminStatusEnum();
        GenericResponse response 
            = authenticationService.upgradeUserToAdmin(userId, accountStatus, adminStatus);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String jsonResponse = gsonBuilder.create().toJson(response);
        String jsonRequest = gsonBuilder.create().toJson(request);
        log.info("request=" + jsonRequest);
        log.info("response=" + jsonResponse);
        log.info("==================== stop api/v1/admin/upgrade-user-status ========================");
        return ResponseEntity.ok(response);
    }
}
