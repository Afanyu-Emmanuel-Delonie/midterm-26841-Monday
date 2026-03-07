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
import org.springframework.web.bind.annotation.RestController;

import com.realestate.brokerage_crm.model.City;
import com.realestate.brokerage_crm.model.Province;
import com.realestate.brokerage_crm.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // Province CRUD
    @PostMapping("/provinces")
    @Operation(summary = "Create province")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = "{\n  \"code\": \"RW-01\",\n  \"name\": \"Kigali\"\n}")))
    public ResponseEntity<Province> createProvince(@RequestBody Province province) {
        Province created = locationService.createProvince(province);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/provinces")
    public ResponseEntity<List<Province>> getAllProvinces() {
        return ResponseEntity.ok(locationService.getAllProvinces());
    }

    @GetMapping("/provinces/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getProvinceById(id));
    }

    @PutMapping("/provinces/{id}")
    public ResponseEntity<Province> updateProvince(@PathVariable Long id, @RequestBody Province province) {
        return ResponseEntity.ok(locationService.updateProvince(id, province));
    }

    @DeleteMapping("/provinces/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        locationService.deleteProvince(id);
        return ResponseEntity.noContent().build();
    }

    // City CRUD (under a province)
    @PostMapping("/provinces/{provinceId}/cities")
    @Operation(summary = "Create city in a province")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = "{\n  \"name\": \"Gasabo\"\n}")))
    public ResponseEntity<City> createCity(
            @PathVariable Long provinceId,
            @RequestBody City city) {
        City created = locationService.createCity(provinceId, city);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/provinces/{provinceId}/cities")
    public ResponseEntity<List<City>> getCitiesByProvince(@PathVariable Long provinceId) {
        return ResponseEntity.ok(locationService.getCitiesByProvince(provinceId));
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getCityById(id));
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City city) {
        return ResponseEntity.ok(locationService.updateCity(id, city));
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        locationService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    // Hierarchy
    @GetMapping("/hierarchy")
    public ResponseEntity<List<Province>> getHierarchy() {
        return ResponseEntity.ok(locationService.getHierarchy());
    }
}
