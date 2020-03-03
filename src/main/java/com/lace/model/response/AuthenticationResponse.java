package com.lace.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
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
@EqualsAndHashCode(callSuper = false)
public class AuthenticationResponse {
    
    @JsonProperty("token")
    private String jwtToken;
    
    @JsonProperty("issued_at")
    private LocalDateTime issuedAt;
    
    @JsonProperty("expires_at")
    private LocalDateTime expiresAt;
    
    private String issuer;
}
