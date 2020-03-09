package com.lace.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class PasswordUpdateRequest {
  
  @NotNull(message = "{password.required}")
  @Size(min = 8, max = 255, message = "{password.size}")
  @NotBlank(message = "{password.blank}")
  private String oldPassword;
  
  @NotNull(message = "{password.new.required}")
  @Size(min = 8, max = 255, message = "{password.new.size}")
  @NotBlank(message = "{password.new.blank}")
  private String newPassword;
  
  @NotNull(message = "password.confirm.required")
  @Size(min = 8, max = 255, message = "{password.confirm.size}")
  @NotBlank(message = "{password.confirm.blank}")
  private String confirmNewPassword;
  
  @NotNull(message = "email.required")
  @Size(min = 5, max = 255, message = "{email.size}")
  @NotBlank(message = "{email.blank}")
  @Email(message = "{email.valid}")
  private String email;
  
  private Long id;
}
