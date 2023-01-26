package com.skdlsco.donelib.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skdlsco.donelib.domain.entity.OAuthInfo;
import com.skdlsco.donelib.domain.entity.OAuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.skdlsco.donelib.domain.entity.QOAuthInfo.oAuthInfo;

@Repository
@RequiredArgsConstructor
public class OAuthInfoRepositoryImpl implements OAuthInfoRepositoryCustom {
    final private JPAQueryFactory queryFactory;

    @Override
    public Optional<OAuthInfo> findOAuthInfo(OAuthType oAuthType, String oAuthId) {
        OAuthInfo findOAuthInfo = queryFactory
                .select(oAuthInfo)
                .from(oAuthInfo)
                .where(oAuthInfo.oAuthType.eq(oAuthType), oAuthInfo.oAuthId.eq(oAuthId))
                .fetchOne();

        return Optional.ofNullable(findOAuthInfo);
    }
}
