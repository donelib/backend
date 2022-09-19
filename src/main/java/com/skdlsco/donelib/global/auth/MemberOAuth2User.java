package com.skdlsco.donelib.global.auth;

import com.skdlsco.donelib.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collections;
import java.util.Map;

public class MemberOAuth2User extends DefaultOAuth2User {

    @Getter
    private Member member;

    public MemberOAuth2User(Member member, Map<String, Object> attributes, String nameAttributeKey) {
        super(Collections.singleton(RoleType.getDefaultAuthority()), attributes, nameAttributeKey);
        this.member = member;
    }
}
