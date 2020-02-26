package com.lace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.enums.AccountStatusEnum;
import com.lace.enums.AdminStatusEnum;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

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

  @Column(name = "last_login_time")
  @JsonProperty("last_login_time")
  private LocalDateTime lastLoginTime;

  @JsonIgnore
  private String password;

  private Long status;
  
  private String username;
  
  @Column(name = "created_by", columnDefinition = "VARCHAR(255) DEFAULT 'admin'")
  @JsonProperty("created_by")
  @CreatedBy
  private String createdBy;

  @Column(name = "updated_by", columnDefinition = "VARCHAR(255) DEFAULT 'admin'")
  @JsonProperty("updated_by")
  @LastModifiedBy
  private String updatedBy;
}
