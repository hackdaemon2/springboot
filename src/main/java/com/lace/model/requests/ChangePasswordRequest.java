package com.lace.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
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
@EqualsAndHashCode
public class ChangePasswordRequest {
 
    @NotNull(message = "{password.required}")
    @NotBlank(message = "{password.blank}")
    @Size(min = 8, max = 255, message = "{password.size}")
    @JsonProperty("old_password")
    private String oldPassword;
    
    @NotNull(message = "{password.new.required}")
    @NotBlank(message = "{password.new.blank}")
    @Size(min = 8, max = 255, message = "{password.new.size}")
    @JsonProperty("new_password")
    private String newPassword;
    
    @Pattern(regexp = "^[0-9]{11}$")
    @NotNull(message = "{mobilenumber.required}")
    @NotBlank(message = "{mobilenumber.blank}")
    @Size(min = 11, max = 11, message = "{mobilenumber.size}")
    @JsonProperty("mobile_number")
    private String mobileNumber;
}
