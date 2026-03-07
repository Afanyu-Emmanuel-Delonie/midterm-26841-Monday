package com.realestate.brokerage_crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.brokerage_crm.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

}
