package com.lace.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.enums.AdminStatusEnum;
import com.lace.enums.AccountStatusEnum;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@EqualsAndHashCode
@Builder
public class UserUpgradeRequest {

    @NotNull(message = "{id.required}")
    @NotBlank(message = "{id.blank}")
    private Long id;
    
    @Builder.Default
    @JsonProperty("admin_status")
    private AdminStatusEnum adminStatusEnum = AdminStatusEnum.ADMIN;
    
    @Builder.Default
    @JsonProperty("account_status")
    private AccountStatusEnum accountStatusEnum = AccountStatusEnum.ACTIVE;
    
}