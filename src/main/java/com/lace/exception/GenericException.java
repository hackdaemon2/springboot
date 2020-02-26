package com.lace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author hackdaemon
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException {

  /**
   * Constructor for BadRequestException
   * 
   * @param message the message to be displayed
   */
  public GenericException(String message) {
    super(message); // call the parent class constructor
  }
}
