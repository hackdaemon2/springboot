package com.lace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author hackdaemon
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    /**
     * Constructor for BadRequestException
     *
     * @param message the message to be displayed
     */
    public BadRequestException(String message) {
        super(message); // call the parent class constructor
    }
}
