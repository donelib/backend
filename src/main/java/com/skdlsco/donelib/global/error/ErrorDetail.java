package com.skdlsco.donelib.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDetail {
    private final String name;
    private final String reason;

    public static ErrorDetail of(String name) {
        return of(name, "");
    }

    public static ErrorDetail of(String name, String reason) {
        return new ErrorDetail(name, reason);
    }
}
