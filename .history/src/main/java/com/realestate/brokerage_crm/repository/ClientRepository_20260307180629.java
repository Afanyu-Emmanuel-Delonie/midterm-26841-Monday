package com.realestate.brokerage_crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.brokerage_crm.model.City;
import com.realestate.brokerage_crm.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
     boolean existsByNameAndProvinceId(String name, Long clientId);
    List<City> findByProvinceId(Long clientId);
}
