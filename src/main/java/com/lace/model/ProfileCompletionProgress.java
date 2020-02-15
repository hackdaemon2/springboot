package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.enums.ProfileCompletion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name="tbl_profile_completion")
public class ProfileCompletionProgress extends AbstractEntity 
        implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Enumerated(value = EnumType.STRING)
  @JsonProperty("profile_completion")
  private ProfileCompletion profileCompletion;

  @OneToMany
  @NotNull
  @Builder.Default
  @JoinColumn(name = "user_id")
  private List<User> user = new ArrayList<>();
}
