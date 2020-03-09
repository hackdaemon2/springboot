package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.enums.AccountStatusEnum;
import com.lace.enums.AdminStatusEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * please change admin status and status fields to enumeration
 *
 * @author hackdaemon
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_admin")
public class AdminEntity extends AbstractEntity {

    @Builder.Default
    @Column(name = "account_status")
    @JsonProperty("account_status")
    private AccountStatusEnum accountStatus = AccountStatusEnum.ACTIVE;

    @Column(name = "admin_status")
    @JsonProperty("admin_status")
    @Builder.Default
    private AdminStatusEnum adminStatus = AdminStatusEnum.ADMIN;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
