package com.softdesign.livetest.util.exception.model;

import org.springframework.http.HttpStatus;

public interface BaseApplicationError extends BaseError {
    HttpStatus getHttpStatus();
}
