package com.realestate.brokerage_crm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locations")
@Data 
@NoArgsConstructor
@A
public class Location {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ProvenceName;

    @Column(nullable = false, unique = true)
    private String ProvinceCode;

    private String city;

    private String postalCode;
}
