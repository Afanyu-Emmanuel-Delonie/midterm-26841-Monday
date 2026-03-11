package com.realestate.brokerage_crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.brokerage_crm.model.Property;
import com.realestate.brokerage_crm.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // Create
    @PostMapping
    @Operation(summary = "Create property")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = "{\n  \"title\": \"3BR Apartment\",\n  \"price\": 85000,\n  \"status\": \"AVAILABLE\",\n  \"agent\": { \"id\": 1 },\n  \"village\": { \"id\": 1 }\n}")))
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        Property created = propertyService.createProperty(property);
        return ResponseEntity.status(201).body(created);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property property) {
        return ResponseEntity.ok(propertyService.updateProperty(id, property));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    // Filter by village or agent
    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchProperties(
            @RequestParam(required = false) Long villageId,
            @RequestParam(required = false) Long agentId) {
        if (villageId != null) {
            return ResponseEntity.ok(propertyService.getPropertiesByVillage(villageId));
        }
        if (agentId != null) {
            return ResponseEntity.ok(propertyService.getPropertiesByAgent(agentId));
        }
        return ResponseEntity.ok(propertyService.getAllProperties());
    }
}
