package com.backend.autocarrerbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AutoCarrerBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoCarrerBridgeApplication.class, args);
    }
}
