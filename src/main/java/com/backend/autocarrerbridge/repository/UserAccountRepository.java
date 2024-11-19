package com.backend.autocarrerbridge.repository;


import com.backend.autocarrerbridge.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findByUsername(String username);
}
