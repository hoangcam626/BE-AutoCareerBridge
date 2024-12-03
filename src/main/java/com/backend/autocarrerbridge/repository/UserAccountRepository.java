package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT ua FROM UserAccount ua JOIN FETCH ua.username WHERE ua.id = :id")
    UserAccount findUserAccountWithUniversityById(@Param("id") Integer id);
}
