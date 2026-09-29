package com.softdesign.livetest.api.exception;

import com.softdesign.livetest.util.exception.response.ApiErrorField;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.softdesign.livetest.api.exception.ApplicationError.INVALID_QUANTITY;
import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void returnsErrorDetailsAndApplicationStatus() {
        var fields = List.of(new ApiErrorField().setField("quantidade").setMessage("Deve ser positiva"));
        var exception = new ServiceException(INVALID_QUANTITY, fields, "produto-123");

        var response = handler.handleServiceException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .isEqualTo(new ErrorResponse(
                        "SALE-004",
                        "Quantidade inválida do produto produto-123",
                        null,
                        fields));
    }
}
