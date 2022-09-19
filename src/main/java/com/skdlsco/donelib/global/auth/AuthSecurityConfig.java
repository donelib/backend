package com.skdlsco.donelib.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class AuthSecurityConfig {
    final private MemberOAuth2UserService memberOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.oauth2Login()
                .authorizationEndpoint()
                .baseUri("/auth/oauth/login")
                .and()

                .redirectionEndpoint()
                .baseUri("/auth/oauth/callback/*")
                .and()

                .userInfoEndpoint()
                .userService(memberOAuth2UserService);
        return http.build();
    }
}
