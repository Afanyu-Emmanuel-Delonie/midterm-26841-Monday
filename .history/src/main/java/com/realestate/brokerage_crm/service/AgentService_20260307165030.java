package com.realestate.brokerage_crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {
    
    @Autowired
    private AgentRepository agentRepository;
}
