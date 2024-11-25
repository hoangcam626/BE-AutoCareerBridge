package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Business;

import com.backend.autocarrerbridge.entity.WorkshopBusiness;
import com.backend.autocarrerbridge.util.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkShopBusinessRepository extends JpaRepository<WorkshopBusiness,Integer> {
    @Query("select bs from Business bs join WorkshopBusiness wsb on wsb.business.id = bs.id where wsb.workshop.id = :workShopId and wsb.statusConnected =:state")
    Page<Business> getAllBusiness(@Param("workShopId") Integer workShopId, Pageable pageable, State state);

    boolean existsByWorkshopIdAndBusinessId(Integer workshopId, Integer businessId);

}
