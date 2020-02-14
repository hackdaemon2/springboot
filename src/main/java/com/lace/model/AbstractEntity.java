package com.lace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author hackdaemon
 */
@Data
@Builder
@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @JsonIgnore
  @Builder.Default
  private boolean deleted = false;

  @JsonIgnore
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  @JsonIgnore
  @Builder.Default
  private LocalDateTime updatedAt = LocalDateTime.now();

}
