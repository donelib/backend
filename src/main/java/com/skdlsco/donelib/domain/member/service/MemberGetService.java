package com.skdlsco.donelib.domain.member.service;

import com.skdlsco.donelib.domain.entity.Member;

public interface MemberGetService {
    Member getById(Long memberId);
}
