package com.skdlsco.donelib.domain.member.repository;

import com.skdlsco.donelib.domain.entity.OAuthInfo;
import com.skdlsco.donelib.domain.entity.OAuthType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthInfoRepository extends JpaRepository<OAuthInfo, Long> {
    @Query("select o from OAuthInfo o where o.oAuthType = :oAuthType and o.oAuthId =:oAuthId")
    Optional<OAuthInfo> findOAuthInfo(OAuthType oAuthType, String oAuthId);
}
