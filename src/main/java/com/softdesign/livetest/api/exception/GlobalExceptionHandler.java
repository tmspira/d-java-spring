package com.softdesign.livetest.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException exception) {
        var error = exception.getError();
        var response = new ErrorResponse(
                error.getCode(),
                exception.getMessage(),
                exception.getData(),
                exception.getFields());

        return ResponseEntity.status(error.getHttpStatus()).body(response);
    }
}
