package com.realestate.brokerage_crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.brokerage_crm.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    boolean existsByEmail(String email);

    Optional<Agent> findByEmail(String email);
}
