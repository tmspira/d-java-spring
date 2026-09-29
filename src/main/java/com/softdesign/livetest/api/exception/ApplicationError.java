package com.softdesign.livetest.api.exception;

import com.softdesign.livetest.util.exception.model.BaseApplicationError;
import org.springframework.http.HttpStatus;

public enum ApplicationError implements BaseApplicationError {

    PRODUCT_NOT_FOUND("SALE-001", HttpStatus.NOT_FOUND, Boolean.TRUE, "Produto não encontrado {}"),
    CLIENT_NOT_FOUND("SALE-002", HttpStatus.NOT_FOUND, Boolean.TRUE, "Cliente não encontrado {}"),
    SALE_NOT_FOUND("SALE-003", HttpStatus.NOT_FOUND, Boolean.TRUE, "Venda não encontrada {}"),
    INVALID_QUANTITY("SALE-004", HttpStatus.BAD_REQUEST, Boolean.TRUE, "Quantidade inválida do produto {}");

    private final String code;
    private final String description;
    private final HttpStatus httpStatus;
    private final boolean acceptParameters;

    ApplicationError(String code, HttpStatus httpStatus, boolean acceptParameters, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
        this.acceptParameters = acceptParameters;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean getAcceptParameters() {
        return acceptParameters;
    }
}
