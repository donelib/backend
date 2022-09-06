package com.skdlsco.donelib.domain.member.service;

import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.OAuthInfo;
import com.skdlsco.donelib.domain.entity.OAuthType;
import com.skdlsco.donelib.domain.member.repository.MemberRepository;
import com.skdlsco.donelib.domain.member.repository.OAuthInfoRepository;
import com.skdlsco.donelib.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberAuthMangerServiceImpl implements MemberAuthManageService {
    final private MemberRepository memberRepository;
    final private OAuthInfoRepository oAuthInfoRepository;

    @Transactional
    @Override
    public Member signup(OAuthType oAuthType, String oauthId) {
        OAuthInfo oAuthInfo = new OAuthInfo(oAuthType, oauthId);
        Member member = new Member();
        oAuthInfo.updateMember(member);
        memberRepository.save(member);
        oAuthInfoRepository.save(oAuthInfo);
        return member;
    }

    @Override
    public Member login(OAuthType oAuthType, String oauthId) {
        Optional<OAuthInfo> oAuthInfo = oAuthInfoRepository.findOAuthInfo(oAuthType, oauthId);
        return oAuthInfo.orElseThrow(UnauthorizedException::new).getMember();
    }
}
