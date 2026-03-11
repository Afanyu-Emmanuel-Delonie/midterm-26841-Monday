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

import com.realestate.brokerage_crm.model.District;
import com.realestate.brokerage_crm.model.Province;
import com.realestate.brokerage_crm.model.Sector;
import com.realestate.brokerage_crm.model.Village;
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

    // District CRUD (under a province)
    @PostMapping("/provinces/{provinceId}/districts")
    @Operation(summary = "Create district in a province")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = "{\n  \"name\": \"Gasabo\"\n}")))
    public ResponseEntity<District> createDistrict(
            @PathVariable Long provinceId,
            @RequestBody District district) {
        District created = locationService.createDistrict(provinceId, district);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/provinces/{provinceId}/districts")
    public ResponseEntity<List<District>> getDistrictsByProvince(@PathVariable Long provinceId) {
        return ResponseEntity.ok(locationService.getDistrictsByProvince(provinceId));
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<District> getDistrictById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getDistrictById(id));
    }

    @PutMapping("/districts/{id}")
    public ResponseEntity<District> updateDistrict(@PathVariable Long id, @RequestBody District district) {
        return ResponseEntity.ok(locationService.updateDistrict(id, district));
    }

    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        locationService.deleteDistrict(id);
        return ResponseEntity.noContent().build();
    }

    // Sector CRUD (under a district)
    @PostMapping("/districts/{districtId}/sectors")
    @Operation(summary = "Create sector in a district")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = "{\n  \"name\": \"Kinyinya\"\n}")))
    public ResponseEntity<Sector> createSector(
            @PathVariable Long districtId,
            @RequestBody Sector sector) {
        Sector created = locationService.createSector(districtId, sector);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/districts/{districtId}/sectors")
    public ResponseEntity<List<Sector>> getSectorsByDistrict(@PathVariable Long districtId) {
        return ResponseEntity.ok(locationService.getSectorsByDistrict(districtId));
    }

    @GetMapping("/sectors/{id}")
    public ResponseEntity<Sector> getSectorById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getSectorById(id));
    }

    @PutMapping("/sectors/{id}")
    public ResponseEntity<Sector> updateSector(@PathVariable Long id, @RequestBody Sector sector) {
        return ResponseEntity.ok(locationService.updateSector(id, sector));
    }

    @DeleteMapping("/sectors/{id}")
    public ResponseEntity<Void> deleteSector(@PathVariable Long id) {
        locationService.deleteSector(id);
        return ResponseEntity.noContent().build();
    }

    // Village CRUD (under a sector)
    @PostMapping("/sectors/{sectorId}/villages")
    @Operation(summary = "Create village in a sector")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = "{\n  \"name\": \"Gasharu\"\n}")))
    public ResponseEntity<Village> createVillage(
            @PathVariable Long sectorId,
            @RequestBody Village village) {
        Village created = locationService.createVillage(sectorId, village);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/sectors/{sectorId}/villages")
    public ResponseEntity<List<Village>> getVillagesBySector(@PathVariable Long sectorId) {
        return ResponseEntity.ok(locationService.getVillagesBySector(sectorId));
    }

    @GetMapping("/villages/{id}")
    public ResponseEntity<Village> getVillageById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getVillageById(id));
    }

    @PutMapping("/villages/{id}")
    public ResponseEntity<Village> updateVillage(@PathVariable Long id, @RequestBody Village village) {
        return ResponseEntity.ok(locationService.updateVillage(id, village));
    }

    @DeleteMapping("/villages/{id}")
    public ResponseEntity<Void> deleteVillage(@PathVariable Long id) {
        locationService.deleteVillage(id);
        return ResponseEntity.noContent().build();
    }

    // Hierarchy
    @GetMapping("/hierarchy")
    public ResponseEntity<List<Province>> getHierarchy() {
        return ResponseEntity.ok(locationService.getHierarchy());
    }
}
