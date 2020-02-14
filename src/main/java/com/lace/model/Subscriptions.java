package com.lace.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
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
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_subscriptions")
@XmlRootElement
@EqualsAndHashCode(callSuper = false)
public class Subscriptions extends AbstractEntity
        implements Serializable {

  private static final long serialVersionUID = 1L; 

  private String name;
  private BigDecimal price;
  
  @Column(name = "created_by", columnDefinition = "VARCHAR(255) NOT NULL DEFAULT 'admin'")
  @JsonProperty("created_by")
  @CreatedBy
  private String createdBy;

  @Column(name = "updated_by", columnDefinition = "VARCHAR(255) NOT NULL DEFAULT 'admin'")
  @JsonProperty("updated_by")
  @LastModifiedBy
  private String updatedBy;
}
