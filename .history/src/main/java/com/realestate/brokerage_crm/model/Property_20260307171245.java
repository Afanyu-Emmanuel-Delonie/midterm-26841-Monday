package com.realestate.brokerage_crm.model;

import org.hibernate.annotations.ValueGenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Property {
    
    @Id
    @ValueGenerationType(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double price;
    private String location;
    private String status;

    @
}
