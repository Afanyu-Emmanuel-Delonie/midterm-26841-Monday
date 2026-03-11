package com.realestate.brokerage_crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.brokerage_crm.model.Village;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {
    boolean existsByNameAndSectorId(String name, Long sectorId);
    List<Village> findBySectorId(Long sectorId);
}
