package com.skdlsco.donelib.global.error.exception;

import com.skdlsco.donelib.global.error.code.GlobalErrorCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(GlobalErrorCode.UNAUTHORIZED);
    }
}
