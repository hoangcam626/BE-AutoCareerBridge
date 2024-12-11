package com.backend.autocarrerbridge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.autocarrerbridge.entity.University;

public interface UniversityRepository extends JpaRepository<University, Integer> {
    University findByPhone(String phone);

    University findByEmail(String email);

    /**
     * Tìm University qua id
     */
    @Query("SELECT u FROM University u WHERE u.id = :id")
    University getUniversityById(Integer id);

    @Query(
            "SELECT u FROM University u WHERE (:address IS NULL OR u.location.province.name = :address) AND (:name IS NULL OR u.name = :name)")
    List<University> findUniversity(@Param("address") String address, @Param("name") String name);

    @Query("SELECT u " +
            "FROM University u " +
            "WHERE u.userAccount.state = :state  " +
            "AND (:keyword IS NULL OR " +
            "     LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "     LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN (u.name) = :keyword THEN 1 " +
            "       WHEN u.email = :keyword THEN 1" +
            "       WHEN CONCAT(u.name, ' ', u.email, ' ') LIKE %:keyword% THEN 2 " +
            "       ELSE 3 " +
            "   END")
    Page<University> findAllByState(Pageable pageable, Integer state, String keyword);
}
