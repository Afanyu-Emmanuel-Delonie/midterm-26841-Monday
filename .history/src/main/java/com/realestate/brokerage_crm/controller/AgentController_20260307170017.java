package com.realestate.brokerage_crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.brokerage_crm.model.Agent;
import com.realestate.brokerage_crm.service.AgentService;

@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {
    
    @Autowired
    private AgentService agentService;

    //Create
    @PostMapping( value = "/register", conumes = "application/json")
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent){
        String
    }
}
