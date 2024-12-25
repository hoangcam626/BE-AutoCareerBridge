package com.backend.autocarrerbridge.repository;

import java.util.List;

import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.dto.response.university.UniversityTotalResponse;
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
            "     LOWER(u.name) LIKE :keyword ESCAPE '\\' OR " +
            "     LOWER(u.email) LIKE :keyword ESCAPE '\\' ) " +
            "ORDER BY u.updatedAt DESC ")
    Page<University> findAllByState(Pageable pageable, State state, String keyword);

    @Query("SELECT u " +
            "FROM University u " +
            "WHERE :keyword IS NULL OR " +
            "     LOWER(u.name) LIKE :keyword ESCAPE '\\' OR " +
            "     LOWER(u.email) LIKE :keyword ESCAPE '\\' " +
            "ORDER BY u.updatedAt DESC ")
    Page<University> findAll(Pageable pageable, String keyword);

    @Query("SELECT new com.backend.autocarrerbridge.dto.response.university.UniversityTotalResponse(u.id,u.name) from University u")
    List<UniversityTotalResponse> getUniversityTotal();
}
