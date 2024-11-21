package com.backend.autocarrerbridge.config;

import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.repository.RoleRepository;
import com.backend.autocarrerbridge.repository.UserAccountRepository;
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


    @Bean
    ApplicationRunner applicationRunner(UserAccountRepository accountRepository) {
        Role role = roleRepository.findById(3).orElse(null);
        return args -> {
            if (accountRepository.findByUsername("admin") == null) {
                UserAccount userAccounts = UserAccount.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(role)  // Sử dụng Role.ADMIN.name() để lưu chuỗi
                        .build();
                accountRepository.save(userAccounts);
            }
        };
    }
}
