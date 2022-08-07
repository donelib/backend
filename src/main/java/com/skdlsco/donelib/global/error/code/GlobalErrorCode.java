package com.skdlsco.donelib.global.error.code;

import lombok.Getter;

@Getter
public enum GlobalErrorCode implements ErrorCode {
    INTERVAL_SERVER_ERROR(500, "G001", "Server error"),
    PAGE_NOT_FOUND(404, "G002", "Page not found"),
    ENTITY_NOT_FOUND(404, "G003", "Entity not found"),
    ;
    private final int status;
    private final String code;
    private final String message;

    GlobalErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}