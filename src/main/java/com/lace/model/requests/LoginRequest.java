package com.lace.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author hackdaemon
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginRequest {

    @NotNull(message = "{mobilenumber.required}")
    @JsonProperty("mobile_number")
    private String mobileNumber;

    @NotNull(message = "{password.required}")
    @JsonProperty("password")
    private String password;
}
