package com.skdlsco.donelib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DonelibApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonelibApplication.class, args);
    }

}
