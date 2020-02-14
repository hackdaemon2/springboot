package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@Entity
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "tbl_country")
public class Country extends AbstractEntity
        implements Serializable {

  private final long serialVersionUID = 1L;

  private String name;

  @Column(name = "created_by", columnDefinition = "VARCHAR(255) NOT NULL DEFAULT 'admin'")
  @JsonProperty("created_by")
  @CreatedBy
  private String createdBy;

  @Column(name = "updated_by", columnDefinition = "VARCHAR(255) NOT NULL DEFAULT 'admin'")
  @JsonProperty("updated_by")
  @LastModifiedBy
  private String updatedBy;
}
