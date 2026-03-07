package com.realestate.brokerage_crm.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class AgentProfile {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

}
