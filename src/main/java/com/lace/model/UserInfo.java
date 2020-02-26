package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.enums.GenderEnum;
import com.lace.enums.ProfileVerificationEnum;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UserInfo {

  @Enumerated(value = EnumType.STRING)
  @Builder.Default
  private GenderEnum gender = GenderEnum.MALE;

  @Enumerated(value = EnumType.STRING)
  @JsonProperty("profile_verification")
  @Builder.Default
  private ProfileVerificationEnum profileVerification = ProfileVerificationEnum.UNVERIFIED;
  
  @Builder.Default
  @Column(nullable = false, name = "is_admin")
  @JsonProperty("is_admin")
  private Boolean isAdmin = false;
  
  @Builder.Default
  @JsonProperty("is_staff")
  @Column(nullable = false, name = "is_staff")
  private Boolean isStaff = false;
  
  @Column(name = "bio_data", columnDefinition = "LONGTEXT", nullable = true)
  @JsonProperty("bio_data")
  private String bioData;
  
  @Column(nullable = true)
  private String nickname;
  
  @Column(nullable = true, length = 6)
  @JsonProperty("confirmation_code")
  private String confirmationCode;
}
