package com.realestate.brokerage_crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.brokerage_crm.model.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    boolean existsByNameAndDistrictId(String name, Long districtId);
    List<Sector> findByDistrictId(Long districtId);
}
