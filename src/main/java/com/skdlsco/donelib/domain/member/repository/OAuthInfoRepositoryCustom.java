package com.skdlsco.donelib.domain.member.repository;

import com.skdlsco.donelib.domain.entity.OAuthInfo;
import com.skdlsco.donelib.domain.entity.OAuthType;

import java.util.Optional;

public interface OAuthInfoRepositoryCustom {
    Optional<OAuthInfo> findOAuthInfo(OAuthType oAuthType, String oAuthId);
}
