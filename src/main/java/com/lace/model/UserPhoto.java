package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Builder
@Entity
@Table(name = "tbl_user_photo")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserPhoto extends AbstractEntity {
  
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String image;
  private String description;

  @Column(nullable = false, name = "is_active")
  @JsonProperty("is_active")
  @Builder.Default
  private Boolean isActive = false;

  @Column(nullable = false, name = "is_profile_image")
  @JsonProperty("is_profile_image")
  @Builder.Default
  private Boolean isProfileImage = false;
}
