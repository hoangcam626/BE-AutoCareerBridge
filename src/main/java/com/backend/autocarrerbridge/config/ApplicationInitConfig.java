package com.backend.autocarrerbridge.config;

import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.repository.RoleRepository;
import com.backend.autocarrerbridge.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
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
