package com.skdlsco.donelib.domain.member.service;

import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.OAuthInfo;
import com.skdlsco.donelib.domain.entity.OAuthType;
import com.skdlsco.donelib.domain.test.DomainJpaTest;
import com.skdlsco.donelib.domain.test.EntityUtil;
import com.skdlsco.donelib.global.error.exception.UnauthorizedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.skdlsco.donelib.domain.test.EntityUtil.generate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DomainJpaTest
class MemberAuthMangerServiceImplTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    MemberAuthMangerServiceImpl memberAuthMangerService;

    @Test
    void signup() {
        //given
        OAuthType type = OAuthType.GOOGLE;
        String oAuthId = "testid";

        //when
        Member member = memberAuthMangerService.signup(type, oAuthId);
        OAuthInfo oAuthInfo = (OAuthInfo) em.createQuery("select o from OAuthInfo o where oAuthId = :oAuthId and oAuthType = :oAuthType")
                .setParameter("oAuthType", type)
                .setParameter("oAuthId", oAuthId)
                .getSingleResult();

        //then
        assertThat(oAuthInfo.getOAuthId()).isEqualTo("testid");
        assertThat(oAuthInfo.getOAuthType()).isEqualTo(OAuthType.GOOGLE);
        assertThat(oAuthInfo.getMember()).isEqualTo(member);
    }

    @Test
    void login() {
        //given
        Member member = generate(em).member();
        OAuthType type = OAuthType.GOOGLE;
        String oAuthId = "testid";
        generate(em).oAuthInfo(member, type, oAuthId);

        //when
        Member loginMember = memberAuthMangerService.login(type, oAuthId);

        //then
        assertThat(loginMember).isEqualTo(member);
    }

    @Test
    void login_notExist_failure() {
        assertThrowsExactly(UnauthorizedException.class, () -> {
            memberAuthMangerService.login(OAuthType.GOOGLE, "testid");
        });
    }
}