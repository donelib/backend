package com.skdlsco.donelib.global.error.exception;

import com.skdlsco.donelib.global.error.ErrorDetail;
import com.skdlsco.donelib.global.error.code.GlobalErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends BusinessException {
    private final ErrorDetail errorDetail;

    public BadRequestException() {
        this(GlobalErrorCode.BAD_REQUEST.getMessage(), null);
    }

    public BadRequestException(ErrorDetail errorDetail) {
        this(GlobalErrorCode.BAD_REQUEST.getMessage(), errorDetail);
    }

    public BadRequestException(String message) {
        this(message, null);
    }

    public BadRequestException(String message, ErrorDetail errorDetail) {
        super(message, GlobalErrorCode.BAD_REQUEST);
        this.errorDetail = errorDetail;
    }
}