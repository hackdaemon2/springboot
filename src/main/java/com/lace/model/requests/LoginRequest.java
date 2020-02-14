package com.lace.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@EqualsAndHashCode(callSuper = false)
public class LoginRequest {

  @NotNull(message = "mobile number cannot be null")
  @NotBlank(message="mobile number must not be blank")
  @Size(min=11, max=11, message = "mobile number must be 11 digits in length")
  @JsonProperty("mobile_number")
  private String mobileNumber;
  
  @NotNull(message = "password cannot be null")
  @NotBlank(message="password must not be blank")
  @Size(min=8, max=8, message = "password must be 8 characters in length")
  @JsonProperty("password")
  private String password;
}
