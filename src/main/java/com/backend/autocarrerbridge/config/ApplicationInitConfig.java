package com.backend.autocarrerbridge.config;

import static com.backend.autocarrerbridge.util.Constant.DEFAULT_USERNAME;
import static com.backend.autocarrerbridge.util.Constant.DESCRIPTION_ADMIN;
import static com.backend.autocarrerbridge.util.Constant.DESCRIPTION_BUSINESS;
import static com.backend.autocarrerbridge.util.Constant.DESCRIPTION_EMPLOYEE;
import static com.backend.autocarrerbridge.util.Constant.DESCRIPTION_SUB_ADMIN;
import static com.backend.autocarrerbridge.util.Constant.DESCRIPTION_UNIVERSITY;
import static com.backend.autocarrerbridge.util.enums.PredefinedRole.ADMIN;
import static com.backend.autocarrerbridge.util.enums.PredefinedRole.BUSINESS;
import static com.backend.autocarrerbridge.util.enums.PredefinedRole.EMPLOYEE;
import static com.backend.autocarrerbridge.util.enums.PredefinedRole.SUB_ADMIN;
import static com.backend.autocarrerbridge.util.enums.PredefinedRole.UNIVERSITY;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.repository.RoleRepository;
import com.backend.autocarrerbridge.repository.UserAccountRepository;
import com.backend.autocarrerbridge.util.enums.State;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationInitConfig {

    final PasswordEncoder passwordEncoder;

    final RoleRepository roleRepository;

    @Value("${security.default.username}")
    private String username;

    @Value("${security.default.password}")
    private String password;

    @Bean
    ApplicationRunner applicationRunner(UserAccountRepository accountRepository) {

        return args -> {
            initializeRoles();
            initializeDefaultAdmin(accountRepository);
        };
    }

    private void initializeRoles() {
        if (roleRepository.findAll().isEmpty()) {
            List<Role> roles = Arrays.asList(
                    new Role(ADMIN.getValue(), ADMIN.name(), DESCRIPTION_ADMIN),
                    new Role(BUSINESS.getValue(), BUSINESS.name(), DESCRIPTION_BUSINESS),
                    new Role(UNIVERSITY.getValue(), UNIVERSITY.name(), DESCRIPTION_UNIVERSITY),
                    new Role(EMPLOYEE.getValue(), EMPLOYEE.name(), DESCRIPTION_EMPLOYEE),
                    new Role(SUB_ADMIN.getValue(), SUB_ADMIN.name(), DESCRIPTION_SUB_ADMIN));
            roleRepository.saveAll(roles);
        }
    }

    private void initializeDefaultAdmin(UserAccountRepository accountRepository) {
        Role role = roleRepository.findById(ADMIN.getValue()).orElse(null);
        if (accountRepository.findByUsername(username) == null) {
            UserAccount userAccounts = UserAccount.builder()
                    .username(DEFAULT_USERNAME)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .state(State.APPROVED)
                    .build();
            accountRepository.save(userAccounts);
        }
    }
}
