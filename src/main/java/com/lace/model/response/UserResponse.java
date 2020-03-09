package com.lace.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.model.Privilege;
import com.lace.model.Role;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author centricgateway
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserResponse {
    
    private Long id;
    
    @JsonProperty("mobile_number")
    private String mobileNumber;
    
    private String email;
    
    @JsonProperty("first_name")
    private String firstName;
    
    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("last_login_time")
    private LocalDateTime lastLoginTime;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    @Builder.Default
    @JsonProperty("user_roles")
    private List<Role> userRoles = new ArrayList<>(1);
    
    @Builder.Default
    @JsonProperty("user_privileges")
    private List<Privilege> privileges = new ArrayList<>(1);
}
