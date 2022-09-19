package com.skdlsco.donelib.global.auth;

import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.OAuthType;
import com.skdlsco.donelib.domain.member.service.MemberAuthManageService;
import com.skdlsco.donelib.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberOAuth2UserService extends DefaultOAuth2UserService {

    final private MemberAuthManageService memberAuthManageService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User baseUser = super.loadUser(userRequest);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = new HashMap<>(baseUser.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthType oAuthType = OAuthType.valueOf(registrationId.toUpperCase());
        String oAuthId = baseUser.getName();

        log.info("try login oAuthType={} oAuthId={}", oAuthType, oAuthId);
        Member loginMember = signUpAndLogin(oAuthType, oAuthId);
        log.info("login memberId={}", loginMember.getId());
        return new MemberOAuth2User(loginMember, attributes, userNameAttributeName);
    }

    private Member signUpAndLogin(OAuthType oAuthType, String oAuthId) {
        try {
            return memberAuthManageService.login(oAuthType, oAuthId);
        } catch (UnauthorizedException e) {
            return memberAuthManageService.signup(oAuthType, oAuthId);
        }
    }
}