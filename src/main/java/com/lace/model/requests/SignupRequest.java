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

    @NotNull(message = "{mobilenumber.required}")
    @Size(min = 11, max = 11, message = "{mobilenumber.size}")
    @NotBlank(message = "{mobilenumber.blank}")
    @JsonProperty("mobile_number")
    private String mobileNumber;

    @NotNull(message = "{password.required}")
    @Size(min = 8, max = 255, message = "{password.size}")
    @JsonProperty("password")
    @NotBlank(message = "{password.blank}")
    private String password;

    @NotNull(message = "{password.confirm.required}")
    @Size(min = 8, max = 255, message = "{password.confirm.size}")
    @NotBlank(message = "{password.confirm.blank}")
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @NotNull(message = "{email.required}")
    @Size(min = 5, max = 255, message = "{email.size}")
    @Email(message = "{email.valid}")
    @NotBlank(message = "{email.blank}")
    private String email;

    @NotNull(message = "{firstname.required}")
    @Size(min = 2, max = 255, message = "{firstname.size}")
    @NotBlank(message = "{firstname.blank}")
    @JsonProperty("first_name")
    private String firstName;

    @NotNull(message = "{lastname.required}")
    @Size(min = 2, max = 255, message = "{lastname.size}")
    @NotBlank(message = "{lastname.blank}")
    @JsonProperty("last_name")
    private String lastName;

    @Builder.Default
    private GenderEnum gender = GenderEnum.MALE;
}
