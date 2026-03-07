package com.realestate.brokerage_crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.brokerage_crm.repository.AgentRepository;

@Service
public class AgentService {
    
    @Autowired
    private AgentRepository agentRepository;

    public String RegisterAgent(String name, String email) {
        if (agentRepository.existsByEmail(email)) {
            return "Error: Email already in use.";
        }
        // Logic to create and save a new agent would go here
        return "Agent registered successfully.";
    }
}
