package com.skdlsco.donelib.domain.member.repository;

import com.skdlsco.donelib.domain.entity.OAuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthInfoRepository extends JpaRepository<OAuthInfo, Long>, OAuthInfoRepositoryCustom {
}
