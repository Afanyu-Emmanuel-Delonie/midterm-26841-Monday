package com.realestate.brokerage_crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.brokerage_crm.model.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    boolean existsByCode(String code);
    Optional<Province> findByCode(String code);
}
