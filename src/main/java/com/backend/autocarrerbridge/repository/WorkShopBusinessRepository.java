package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Workshop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.WorkshopBusiness;
import com.backend.autocarrerbridge.util.enums.State;

import java.time.LocalDateTime;

@Repository
public interface WorkShopBusinessRepository extends JpaRepository<WorkshopBusiness, Integer> {
    @Query("select bs from Business bs " + "join WorkshopBusiness wsb on wsb.business = bs "
            + "where wsb.workshop.id = :workShopId and wsb.statusConnected = :state")
    Page<Business> getAllBusiness(
            @Param("workShopId") Integer workShopId, @Param("state") State state, Pageable pageable);

    @Query("select ws from WorkshopBusiness ws where ws.workshop.id =:workshopId and ws.business.id =:businessId")
    WorkshopBusiness checkExistWorkShop(
            @Param("workshopId") Integer workshopId, @Param("businessId") Integer businessId);

    long countByWorkshopAndStatusConnected(Workshop workshop, State statusConnected);


    @Query("SELECT count(wsb.id) FROM WorkshopBusiness wsb WHERE wsb.status = 1 AND wsb.statusConnected = 1")
    Long countWorkshopBusiness ();

    @Query("SELECT count(wb) FROM WorkshopBusiness wb WHERE wb.createdAt between :date and :nextDate")
    Long countWorkshopBusinessByDate(LocalDateTime date, LocalDateTime nextDate);
}
