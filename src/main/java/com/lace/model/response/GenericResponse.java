package com.lace.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lace.model.User;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author hackdaemon
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class GenericResponse {

  @JsonProperty("response_code")
  private String responseCode;
  
  private String status;
  private String message;
  private List<Object> data;
  private List<User> userData;
  private List<Object> payload;
  private Long rowCount;
  private Long perPage;
  private Long currentPage;
}
