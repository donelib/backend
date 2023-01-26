package com.skdlsco.donelib.domain.member.repository;

import com.skdlsco.donelib.domain.entity.OAuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthInfoRepository extends JpaRepository<OAuthInfo, Long>, OAuthInfoRepositoryCustom {
}
