package com.lace.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.enums.GenderEnum;
import javax.validation.constraints.Email;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class SignupRequest {

  @NotNull(message = "mobile number required")
  @Size(min = 11, max = 11)
  @JsonProperty("mobile_number")
  private String mobileNumber;

  @NotNull(message = "required")
  @Size(min = 8, max = 255)
  @JsonProperty("password")
  private String password;

  @NotNull(message = "required")
  @Size(min = 8, max = 255)
  @JsonProperty("confirm_password")
  private String confirmPassword;

  @NotNull(message = "required")
  @Size(min = 5, max = 255)
  @Email(message = "enter a valid email")
  private String email;

  @NotNull(message = "required")
  @Size(min = 2, max = 255)
  @JsonProperty("first_name")
  private String firstName;

  @NotNull(message = "required")
  @Size(min = 2, max = 255)
  @JsonProperty("last_name")
  private String lastName;

  private GenderEnum gender = GenderEnum.MALE;
}
