package com.lace.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Error response.
 *
 * @author hackdaemon
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ErrorResponse implements Serializable {

  private final static long serialVersionUID = 1L;
  
  private Date timestamp;
  private String status;
  private String message;
  private String details;
  
  @Builder.Default
  @JsonProperty("error_status")
  private boolean errorStatus = false;
}