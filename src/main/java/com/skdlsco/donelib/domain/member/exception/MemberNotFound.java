package com.skdlsco.donelib.domain.member.exception;

import com.skdlsco.donelib.global.error.code.GlobalErrorCode;
import com.skdlsco.donelib.global.error.exception.BusinessException;

public class MemberNotFound extends BusinessException {

    public MemberNotFound() {
        super("Member not found", GlobalErrorCode.ENTITY_NOT_FOUND);
    }
}
