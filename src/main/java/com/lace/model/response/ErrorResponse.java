package com.lace.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.lace.constants.ApplicationConstants.INIT;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {

  private final static long serialVersionUID = 1L;
  
  private LocalDateTime timestamp;
  private String status;
  private String message;
  private String details;
  
  @Builder.Default
  @JsonProperty("error_messages")
  private List<String> errorMessages = new ArrayList<>(INIT);
  
  @Builder.Default
  @JsonProperty("error_status")
  private boolean errorStatus = false;
}