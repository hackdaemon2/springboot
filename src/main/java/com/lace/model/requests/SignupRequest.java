package com.lace.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.enums.GenderEnum;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class SignupRequest {

  @NotNull(message = "mobile number required")
  @Size(min = 11, max = 11, message = "mobile number must be 11 digits")
  @NotBlank(message = "mobile number cannot be blank")
  @JsonProperty("mobile_number")
  private String mobileNumber;

  @NotNull(message = "password is required")
  @Size(min = 8, max = 255, message = "password must be between 8 and 255 characters")
  @JsonProperty("password")
  @NotBlank(message = "password cannot be blank")
  private String password;

  @NotNull(message = "confirm password is required")
  @Size(min = 8, max = 255, message = "confirm password must be between 8 and 255 characters")
  @JsonProperty("confirm_password")
  @NotBlank(message = "confirm password cannot be blank")
  private String confirmPassword;

  @NotNull(message = "email is required")
  @Size(min = 5, max = 255, message = "email must be between 5 and 255 characters")
  @Email(message = "enter a valid email")
  @NotBlank(message = "email cannot be blank")
  private String email;

  @NotNull(message = "first name is required")
  @Size(min = 2, max = 255, message = "first name must be between 2 and 255 characters")
  @JsonProperty("first_name")
  @NotBlank(message = "first name cannot be blank")
  private String firstName;

  @NotNull(message = "last name is required")
  @Size(min = 2, max = 255, message = "last name must be between 2 and 255 characters")
  @JsonProperty("last_name")
  @NotBlank(message = "last name cannot be blank")
  private String lastName;

  @Builder.Default
  private GenderEnum gender = GenderEnum.MALE;
}
