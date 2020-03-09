package com.lace.exception;

import com.lace.model.response.ErrorResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(
        UsernameNotFoundException exception, WebRequest request
    ) {
        ErrorResponse errorDetails;
        errorDetails = ErrorResponse
                        .builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .message("invalid login credentials")
                        .details(request.getDescription(false))
                        .errorStatus(true)
                        .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Resource not found exception response entity.
     *
     * @param exception the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(
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
    public ResponseEntity<?> handleMethodNotValidException(
        MethodArgumentNotValidException exception, WebRequest request
    ) {
        List<String> errors = new ArrayList<>(1);
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();        
        fieldErrors.forEach((error) -> {   
            errors.add(error.getDefaultMessage());
        });
        List<ObjectError> objectErrors = exception.getBindingResult().getGlobalErrors();        
        objectErrors.forEach((error) -> {   
            errors.add(error.getDefaultMessage());
        });
        ErrorResponse errorDetails;
        errorDetails = ErrorResponse
                        .builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .errorMessages(errors)
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
    public ResponseEntity<?> handleResourceNotFoundException(
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
    public ResponseEntity<?> handleResourceConflictException(
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
    public ResponseEntity<?> handleGlobalException(
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
    public ResponseEntity<?> handleGenericException(
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
    public ResponseEntity<?> handleUnauthorizedException(
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
