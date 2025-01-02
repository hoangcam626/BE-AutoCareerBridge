package com.backend.autocarrerbridge.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.autocarrerbridge.dto.response.business.BusinessListHome;
import com.backend.autocarrerbridge.dto.response.business.BusinessSearchPage;
import com.backend.autocarrerbridge.dto.response.university.UniversityListHome;
import com.backend.autocarrerbridge.dto.response.university.UniversitySearchPage;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.dto.response.university.UniversityTotalResponse;
import com.backend.autocarrerbridge.util.enums.Status;
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

    @Query("SELECT count(u.id) from University u where u.status =:status")
    Long countUniversity(@Param("status") Status status);

    @Query("SELECT count(u.id) FROM University u WHERE u.status <> 0 AND u.userAccount.state = 1")
    Long countUniversity();

    @Query("SELECT count(u.id) FROM University u WHERE u.createdAt between :date and :nextDate")
    Long countUniversityByDate(LocalDateTime date, LocalDateTime nextDate);

    @Query("""
    SELECT new com.backend.autocarrerbridge.dto.response.university.UniversityListHome(
        u.id,
        u.name,
        u.description,
        u.logoImageId,
        COUNT(j)
    )
    from University u
    LEFT JOIN BusinessUniversity j ON j.university.id = u.id
    GROUP BY u.id, u.name, u.description, u.logoImageId
    ORDER BY COUNT(j) DESC
    LIMIT 9
    """)
    List<UniversityListHome> getListUniversityFollowNumberJob();

    @Query("""
    SELECT new com.backend.autocarrerbridge.dto.response.university.UniversitySearchPage(
        u.id,
        u.name,
        u.description,
        u.location.province.fullName,
        u.location.district.fullName,
        u.location.ward.fullName,
        u.location.description,
        u.logoImageId,
        COUNT(bu)

    )
    from University u
    LEFT JOIN BusinessUniversity bu ON bu.university.id = u.id AND bu.status = 1 AND bu.statusConnected = 1
    WHERE (:keyword IS NULL OR LOWER(u.name) LIKE :keyword ESCAPE '\\')
    GROUP BY u.id, u.name, u.description, u.location.province.fullName, u.location.district.fullName, u.location.ward.fullName, u.location.description, u.logoImageId
    ORDER BY COUNT(bu) DESC
    """)
    Page<UniversitySearchPage> getUniversityForPaging(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}


