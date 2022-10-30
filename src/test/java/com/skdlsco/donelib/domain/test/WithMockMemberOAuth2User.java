package com.skdlsco.donelib.domain.test;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockMemberOAuth2UserSecurityContextFactory.class)
public @interface WithMockMemberOAuth2User {
}
