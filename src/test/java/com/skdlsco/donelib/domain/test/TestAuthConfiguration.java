package com.skdlsco.donelib.domain.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@TestConfiguration
public class TestAuthConfiguration {

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;
}
