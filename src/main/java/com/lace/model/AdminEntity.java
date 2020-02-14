package com.lace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
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
@Entity
@Table(name = "tbl_admin")
public class AdminEntity extends AbstractEntity
        implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "account_status")
  @JsonProperty("account_status")
  @NotEmpty(message = "Please provide account status")
  private String accountStatus;

  @Column(name = "admin_status")
  @JsonProperty("admin_status")
  private Long adminStatus;

  @Column(name = "last_login_time")
  @JsonProperty("last_login_time")
  private LocalDateTime lastLoginTime;

  @JsonIgnore
  @Size(min = 8, message = "*Your password must have at least 8 characters")
  @NotEmpty(message = "Please provide a password")
  private String password;

  private Long status;
  
  @Size(min = 5, message = "*Your username must have at least 5 characters")
  @NotEmpty(message = "Please provide a username")
  private String username;
  
  @Column(name = "created_by", columnDefinition = "VARCHAR(255) NOT NULL DEFAULT 'admin'")
  @JsonProperty("created_by")
  @CreatedBy
  private String createdBy;

  @Column(name = "updated_by", columnDefinition = "VARCHAR(255) NOT NULL DEFAULT 'admin'")
  @JsonProperty("updated_by")
  @LastModifiedBy
  private String updatedBy;
}
