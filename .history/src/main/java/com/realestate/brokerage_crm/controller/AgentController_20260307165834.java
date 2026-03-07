package com.realestate.brokerage_crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.brokerage_crm.service.AgentService;

@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {
    
    @Autowired
    private AgentService agentService;

    //Create
    @PostMapping( value = "/register", con)
}
