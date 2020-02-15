package com.lace.model.response;

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
@EqualsAndHashCode(callSuper = false)
public class GenericResponse {

  private String responseCode;
  private String status;
  private String message;
  private List<Object> data;
  private List<Object> payload;
  
  public static GenericResponse buildLoginResponse() {
    return GenericResponse
      
            .responseCode(responseCode)
            .status(status)
            .message(message)
            .build();
  }
}
