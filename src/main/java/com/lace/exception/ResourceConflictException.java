package com.lace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author hackdaemon
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {
  /**
   * Instantiates a new Resource Conflict exception.
   *
   * @param message the message
   */
  public ResourceConflictException(String message) {
    super(message);
  }
}
