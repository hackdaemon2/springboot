package com.lace.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author hackdaemon
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginRequest {

  @JsonProperty("mobile_number")
  private String mobileNumber;
  
  @JsonProperty("password")
  private String password;
}
