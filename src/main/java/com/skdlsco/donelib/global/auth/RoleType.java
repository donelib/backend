package com.skdlsco.donelib.global.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleType {
    ROLE_USER;

    static GrantedAuthority getDefaultAuthority() {
        return new SimpleGrantedAuthority(ROLE_USER.name());
    }
}
