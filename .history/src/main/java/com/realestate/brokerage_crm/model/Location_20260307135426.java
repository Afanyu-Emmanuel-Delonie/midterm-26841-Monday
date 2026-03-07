package com.realestate.brokerage_crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locations")
@Data 
@NoArgsConstructor
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
