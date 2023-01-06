package com.skdlsco.donelib.global.error;

import com.skdlsco.donelib.global.error.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;
    private final List<ErrorDetail> errors;
    public static ErrorResponse of(ErrorCode errorCode) {
       return of(errorCode, errorCode.getMessage(), Collections.emptyList());
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return of(errorCode, message, Collections.emptyList());
    }

    public static ErrorResponse of(ErrorCode errorCode, List<ErrorDetail> errors) {
        return of(errorCode, errorCode.getMessage(), errors);
    }

    public static ErrorResponse of(ErrorCode errorCode, String message, List<ErrorDetail> errors) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(message)
                .errors(errors)
                .build();
    }
}