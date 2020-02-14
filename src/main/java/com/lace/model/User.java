package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Entity
@Table(name = "tbl_users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractEntity
        implements Serializable {

  private final long serialVersionUID = 1L;

  @Column(name = "first_name", nullable = false)
  @JsonProperty("first_name")
  private String firstName;

  @Column(name = "last_name", nullable = false)
  @JsonProperty("last_name")
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;
  
  @Column(name = "mobile_number", nullable = false, unique = true)
  @JsonProperty("mobile_number")
  private String mobileNumber;
  
  @Embedded
  private UserInfo userInfo;

  @Builder.Default
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "tbl_user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new HashSet<>(1);
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "country_id")
  private Country country;

  @Column(name = "created_by", columnDefinition = "VARCHAR(255) NOT NULL DEFAULT 'admin'")
  @JsonProperty("created_by")
  @CreatedBy
  private String createdBy;

  @Column(name = "updated_by", columnDefinition = "VARCHAR(255) NOT NULL DEFAULT 'admin'")
  @JsonProperty("updated_by")
  @LastModifiedBy
  private String updatedBy;
  
  @OneToMany
  @JoinColumn("user_id")
  @Builder.Default
  private List<UserPhoto> userPhotos = new ArrayList<>();
  
  public String getFullname() {
    return String.format("%s %s", firstName, lastName);
  }
}