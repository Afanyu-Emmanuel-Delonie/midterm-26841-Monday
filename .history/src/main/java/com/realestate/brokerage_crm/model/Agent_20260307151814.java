package com.realestate.brokerage_crm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationTy.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
