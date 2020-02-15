package com.lace.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * The type Global exception handler.
 *
 * @author hackdaemon
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Resource not found exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {
    ErrorResponse errorDetails; 
    errorDetails = ErrorResponse.builder()
                                .timestamp(new Date())
                                .status(HttpStatus.BAD_REQUEST.toString())
                                .message(ex.getMessage())
                                .details(request.getDescription(false))
                                .errorStatus(true)
                                .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  /**
   * Resource not found exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ErrorResponse errorDetails;
    errorDetails = ErrorResponse.builder()
                                .timestamp(new Date())
                                .status(HttpStatus.NOT_FOUND.toString())
                                .message(ex.getMessage())
                                .details(request.getDescription(false))
                                .errorStatus(true)
                                .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  /**
   * Resource conflict exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(ResourceConflictException.class)
  public ResponseEntity<?> resourceConflictException(ResourceConflictException ex, WebRequest request) {
    ErrorResponse errorDetails;
    errorDetails = ErrorResponse.builder()
                                .timestamp(new Date())
                                .status(HttpStatus.CONFLICT.toString())
                                .message(ex.getMessage())
                                .details(request.getDescription(false))
                                .errorStatus(true)
                                .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
  }

  /**
   * Global exception handler response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorResponse errorDetails;
    errorDetails = ErrorResponse.builder()
                                .timestamp(new Date())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                                .message(ex.getMessage())
                                .details(request.getDescription(false))
                                .errorStatus(true)
                                .build();
    errorDetails.setErrorStatus(true);
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
