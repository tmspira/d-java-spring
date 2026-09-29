package com.softdesign.livetest.api.exception;

import com.softdesign.livetest.util.exception.response.ApiErrorField;

import java.util.List;

public record ErrorResponse(String code, String message, Object data, List<ApiErrorField> fields) {}
