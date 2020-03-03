package com.lace.exception;

import com.lace.model.response.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
   * @param exception the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> badRequestException(
    BadRequestException exception, WebRequest request
  ) {
    ErrorResponse errorDetails;
    errorDetails = ErrorResponse
                      .builder()
                      .timestamp(LocalDateTime.now())
                      .status(HttpStatus.BAD_REQUEST.toString())
                      .message(exception.getMessage())
                      .details(request.getDescription(false))
                      .errorStatus(true)
                      .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
  
  /**
   * 
   * @param exception
   * @param request
   * @return ResponseEntity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> methodNotValidException(
    MethodArgumentNotValidException exception, WebRequest request
  ) {
    ErrorResponse errorDetails; 
    errorDetails = ErrorResponse
                      .builder()
                      .timestamp(LocalDateTime.now())
                      .status(HttpStatus.BAD_REQUEST.toString())
                      .message(exception.getMessage())
                      .details(request.getDescription(false))
                      .errorStatus(true)
                      .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  /**
   * Resource not found exception response entity.
   *
   * @param exception the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundException(
    ResourceNotFoundException exception, WebRequest request
  ) {
    ErrorResponse errorDetails;
    errorDetails = ErrorResponse
                      .builder()
                      .timestamp(LocalDateTime.now())
                      .status(HttpStatus.NOT_FOUND.toString())
                      .message(exception.getMessage())
                      .details(request.getDescription(false))
                      .errorStatus(true)
                      .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  /**
   * Resource conflict exception response entity.
   *
   * @param exception the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(ResourceConflictException.class)
  public ResponseEntity<?> resourceConflictException(
    ResourceConflictException exception, WebRequest request
  ) {
    ErrorResponse errorDetails;
    errorDetails = ErrorResponse
                      .builder()
                      .timestamp(LocalDateTime.now())
                      .status(HttpStatus.CONFLICT.toString())
                      .message(exception.getMessage())
                      .details(request.getDescription(false))
                      .errorStatus(true)
                      .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
  }

  /**
   * Global exception handler response entity.
   *
   * @param exception the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandler(
    Exception exception, WebRequest request
  ) {
    ErrorResponse errorDetails;
    errorDetails = ErrorResponse
                      .builder()
                      .timestamp(LocalDateTime.now())
                      .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                      .message(exception.getMessage())
                      .details(request.getDescription(false))
                      .errorStatus(true)
                      .build();
    errorDetails.setErrorStatus(true);
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  /**
   * Global exception handler response entity.
   *
   * @param exception the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(GenericException.class)
  public ResponseEntity<?> genericExceptionHandler(
    Exception exception, WebRequest request
  ) {
    ErrorResponse errorDetails;
    errorDetails = ErrorResponse
                      .builder()
                      .timestamp(LocalDateTime.now())
                      .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                      .message(exception.getMessage())
                      .details(request.getDescription(false))
                      .errorStatus(true)
                      .build();
    errorDetails.setErrorStatus(true);
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
   /**
   * Resource not found exception response entity.
   *
   * @param exception the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<?> unauthorizedException(
    UnauthorizedException exception, WebRequest request
  ) {
    ErrorResponse errorDetails;
    errorDetails = ErrorResponse
                      .builder()
                      .timestamp(LocalDateTime.now())
                      .status(HttpStatus.UNAUTHORIZED.toString())
                      .message(exception.getMessage())
                      .details(request.getDescription(false))
                      .errorStatus(true)
                      .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
  }
}
