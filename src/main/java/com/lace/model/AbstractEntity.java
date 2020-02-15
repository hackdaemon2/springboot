package com.lace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

/**
 *
 * @author hackdaemon
 */
@Data
@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @JsonIgnore
  private boolean deleted = false;

  @JsonIgnore
  private LocalDateTime createdAt = LocalDateTime.now();

  @JsonIgnore
  private LocalDateTime updatedAt = LocalDateTime.now();

}
