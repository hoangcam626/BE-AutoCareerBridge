package com.backend.autocarrerbridge.config;

import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.repository.RoleRepository;
import com.backend.autocarrerbridge.repository.UserAccountRepository;

import com.backend.autocarrerbridge.util.enums.PredefinedRole;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;
    String defaultUsername = "admin";
    String defaultPassword = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserAccountRepository accountRepository) {
        com.backend.autocarrerbridge.entity.Role role = roleRepository.findById(PredefinedRole.ADMIN.getValue()).orElse(null);
        return args -> {
            if (accountRepository.findByUsername(defaultUsername) == null) {
                UserAccount userAccounts = UserAccount.builder()
                        .username(defaultUsername)
                        .password(passwordEncoder.encode(defaultPassword))
                        .role(role)
                        .build();
                accountRepository.save(userAccounts);
            }
        };
    }
}
