package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 *
 * @author hackdaemon
 */
@Data
@Builder
@Entity
@Table(name = "tbl_settings")
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Settings extends AbstractEntity {

  private String address;
  private String developer;
  private String email;
  private String name;
  private String phone1;
  private String motd;

  @Column(name = "support_phone")
  @JsonProperty("support_phone")
  private String supportPhone;

  private String version;

  @Column(name = "max_invalid_attempt")
  @JsonProperty("max_invalid_attempt")
  private int maxInvalidAttempt;

  @Column(name = "app_code")
  @JsonProperty("app_code")
  private String appCode;

  @Column(name = "is_active")
  @JsonProperty("is_active")
  @Builder.Default
  private Boolean isActive = false;
  
  @Column(name = "created_by", columnDefinition = "VARCHAR(255) DEFAULT 'admin'")
  @JsonProperty("created_by")
  @CreatedBy
  private String createdBy;

  @Column(name = "updated_by", columnDefinition = "VARCHAR(255) DEFAULT 'admin'")
  @JsonProperty("updated_by")
  @LastModifiedBy
  private String updatedBy;
}
