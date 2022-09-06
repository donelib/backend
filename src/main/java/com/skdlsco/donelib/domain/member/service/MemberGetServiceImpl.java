package com.skdlsco.donelib.domain.member.service;

import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.member.exception.MemberNotFound;
import com.skdlsco.donelib.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGetServiceImpl implements MemberGetService {
    final private MemberRepository memberRepository;

    @Override
    public Member getById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);
    }
}
