package com.webone.quiosq.exception;

import java.io.Serial;
import lombok.Generated;
import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6695383790847736493L;
    private final Integer code;
    private final String detail;
    private final HttpStatus httpStatus;

    public BaseException(final String message, final Integer code, final String detail,
        final HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.detail = detail;
        this.httpStatus = httpStatus;
    }

    public BaseException(final String message, final Throwable cause, final Integer code,
        final String detail,
        final HttpStatus httpStatus) {
        super(message, cause);
        this.code = code;
        this.detail = detail;
        this.httpStatus = httpStatus;
    }

    @Generated
    public Integer getCode() {
        return code;
    }

    @Generated
    public String getDetail() {
        return detail;
    }

    @Generated
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
