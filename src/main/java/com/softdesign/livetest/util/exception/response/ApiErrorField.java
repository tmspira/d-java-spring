package com.softdesign.livetest.util.exception.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ApiErrorField {
    private static final long serialVersionUUID = 5576639104940629030L;
    private String field;
    private String type;
    private String message;

    public String getMessage() {
        return message;
    }

    public ApiErrorField setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getType() {
        return type;
    }

    public ApiErrorField setType(String type) {
        this.type = type;
        return this;
    }

    public String getField() {
        return field;
    }

    public ApiErrorField setField(String field) {
        this.field = field;
        return this;
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, true);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }


}
