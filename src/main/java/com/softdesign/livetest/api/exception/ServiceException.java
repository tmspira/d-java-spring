package com.softdesign.livetest.api.exception;

import com.softdesign.livetest.util.exception.model.BaseApplicationError;
import com.softdesign.livetest.util.exception.response.ApiErrorField;
import com.softdesign.livetest.util.Strings;

import java.io.Serial;
import java.util.List;

public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5531865968472944168L;
    private final BaseApplicationError error;
    private final Object[] parameters;
    private final Object data;
    private List<ApiErrorField> fields;

    public ServiceException(BaseApplicationError error, Object... parameters) {
        super(Strings.formatMessage(error.getDescription(), parameters));
        this.error = error;
        this.parameters = parameters;
        this.data = null;
    }

    public ServiceException(BaseApplicationError error, List<ApiErrorField> fields, Object... parameters) {
        super(Strings.formatMessage(error.getDescription(), parameters));
        this.error = error;
        this.parameters = parameters;
        this.data = null;
        this.fields = fields;
    }

    public ServiceException(Object data, BaseApplicationError error, Object... parameters) {
        super(Strings.formatMessage(error.getDescription(), parameters));
        this.error = error;
        this.parameters = parameters;
        this.data = data;
    }

    public ServiceException(Object data, BaseApplicationError error, Throwable cause, Object... parameters) {
        super(Strings.formatMessage(error.getDescription(), parameters), cause);
        this.error = error;
        this.parameters = parameters;
        this.data = data;
    }

    public BaseApplicationError getError() { return this.error; }

    public Object[] getParameters() { return this.parameters; }

    public Object getData() { return this.data; }

    public List<ApiErrorField> getFields() { return this.fields; }

}
