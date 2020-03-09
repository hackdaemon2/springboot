package com.lace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.constants.ApplicationConstants;
import java.time.LocalDateTime;
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
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_users")
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractEntity implements ApplicationConstants {

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "mobile_number", nullable = false, unique = true)
    @JsonProperty("mobile_number")
    private String mobileNumber;

    @Builder.Default
    @Column(name = "last_login_time", nullable = true)
    @JsonProperty("last_login_time")
    private LocalDateTime lastLoginTime = LocalDateTime.now();

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

    @Column(
        name = "created_by",
        columnDefinition = "VARCHAR(255) DEFAULT 'system'",
        nullable = true
    )
    @JsonProperty("created_by")
    @CreatedBy
    private String createdBy;

    @Column(
        name = "updated_by",
        columnDefinition = "VARCHAR(255) DEFAULT 'system'",
        nullable = true
    )
    @JsonProperty("updated_by")
    @LastModifiedBy
    private String updatedBy;

    @OneToMany
    @JoinColumn(name = "user_id")
    @Builder.Default
    private List<UserPhoto> userPhotos = new ArrayList<>(INIT);

    public String getFullname() {
        return String.format("%s %s", firstName, lastName);
    }
}
