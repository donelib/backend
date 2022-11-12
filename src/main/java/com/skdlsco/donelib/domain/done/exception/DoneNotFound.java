package com.skdlsco.donelib.domain.done.exception;

import com.skdlsco.donelib.global.error.code.GlobalErrorCode;
import com.skdlsco.donelib.global.error.exception.BusinessException;

public class DoneNotFound extends BusinessException {

    public DoneNotFound() {
        super("Done not found", GlobalErrorCode.ENTITY_NOT_FOUND);
    }
    public DoneNotFound(String message) {
        super(message, GlobalErrorCode.DONE_NOT_FOUND);
    }
}
