package com.realestate.brokerage_crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.brokerage_crm.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByVillageId(Long villageId);
    List<Property> findByAgentId(Long agentId);
}
