package com.skdlsco.donelib.global.error.code;

import lombok.Getter;

@Getter
public enum GlobalErrorCode implements ErrorCode {
    INTERVAL_SERVER_ERROR(500, "G001", "Server error"),
    PAGE_NOT_FOUND(404, "G002", "Page not found"),
    ENTITY_NOT_FOUND(404, "G003", "Entity not found"),
    BAD_REQUEST(400, "G004", "Bad Request"),
    METHOD_NOW_ALLOWED(405, "G005", "Method Not Allowed"),
    UNAUTHORIZED(401, "g004", "Unauthorized"),
    TAG_NOT_FOUND(404, "T001", "Tag not found"),

    DONE_NOT_FOUND(404, "D001", "Done not found");
    private final int status;
    private final String code;
    private final String message;

    GlobalErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}