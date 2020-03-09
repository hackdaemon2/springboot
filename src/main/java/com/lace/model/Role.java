package com.lace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lace.constants.ApplicationConstants;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "tbl_roles")
public class Role extends AbstractEntity implements ApplicationConstants {
    
    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> user = new HashSet<>(INIT);

    @Column(name = "created_by", columnDefinition = "VARCHAR(255) DEFAULT 'admin'")
    @JsonIgnore
    private String createdBy;

    @ManyToMany
    @JoinTable(
        name = "roles_privileges",
        joinColumns = @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "privilege_id",
            referencedColumnName = "id"
        )
    )
    private Set<Privilege> privileges;

    @Column(name = "updated_by", columnDefinition = "VARCHAR(255) DEFAULT 'admin'")
    @JsonIgnore
    private String updatedBy;
}
