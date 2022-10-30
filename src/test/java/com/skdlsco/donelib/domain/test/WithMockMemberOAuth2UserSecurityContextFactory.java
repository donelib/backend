package com.skdlsco.donelib.domain.test;

import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.global.auth.MemberOAuth2User;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;


@TestComponent
public class WithMockMemberOAuth2UserSecurityContextFactory implements WithSecurityContextFactory<WithMockMemberOAuth2User> {
    public static Member loginMember;
    @PersistenceContext
    EntityManager em;

    @Override
    public SecurityContext createSecurityContext(WithMockMemberOAuth2User annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Member member = EntityUtil.generate(em).member();
        loginMember = member;
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("test", "id");
        MemberOAuth2User memberOAuth2User = new MemberOAuth2User(member, attributes, "test");
        Authentication auth =
                new OAuth2AuthenticationToken(memberOAuth2User, memberOAuth2User.getAuthorities(), "test");
        context.setAuthentication(auth);
        return context;
    }
}
