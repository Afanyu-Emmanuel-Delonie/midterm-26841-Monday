package com.realestate.brokerage_crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.brokerage_crm.model.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    boolean existsByNameAndProvinceId(String name, Long provinceId);
    List<District> findByProvinceId(Long provinceId);
}
