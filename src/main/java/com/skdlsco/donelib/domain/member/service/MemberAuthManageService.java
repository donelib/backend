package com.skdlsco.donelib.domain.member.service;

import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.OAuthType;

public interface MemberAuthManageService {
    Member signup(OAuthType oAuthType, String oauthId);

    Member login(OAuthType oAuthType, String oauthId);
}
