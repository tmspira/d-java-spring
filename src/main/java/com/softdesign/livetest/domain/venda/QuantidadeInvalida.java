package com.softdesign.livetest.domain.venda;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Quantidade inválida")
public class QuantidadeInvalida extends RuntimeException {

    public QuantidadeInvalida() {
    }

    public QuantidadeInvalida(String message) {
        super(message);
    }

    public QuantidadeInvalida(String message, Throwable cause) {
        super(message, cause);
    }

    public QuantidadeInvalida(Throwable cause) {
        super(cause);
    }

    public QuantidadeInvalida(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

