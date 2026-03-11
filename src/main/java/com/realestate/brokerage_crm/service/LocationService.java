package com.realestate.brokerage_crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realestate.brokerage_crm.exception.DuplicateResourceException;
import com.realestate.brokerage_crm.exception.ResourceNotFoundException;
import com.realestate.brokerage_crm.model.District;
import com.realestate.brokerage_crm.model.Province;
import com.realestate.brokerage_crm.model.Sector;
import com.realestate.brokerage_crm.model.Village;
import com.realestate.brokerage_crm.repository.DistrictRepository;
import com.realestate.brokerage_crm.repository.ProvinceRepository;
import com.realestate.brokerage_crm.repository.SectorRepository;
import com.realestate.brokerage_crm.repository.VillageRepository;

@Service
public class LocationService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private VillageRepository villageRepository;

    // Province CRUD
    public Province createProvince(Province province) {
        if (provinceRepository.existsByCode(province.getCode())) {
            throw new DuplicateResourceException("Province code already exists.");
        }
        return provinceRepository.save(province);
    }

    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    public Province getProvinceById(Long id) {
        return provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found"));
    }

    public Province updateProvince(Long id, Province payload) {
        Province existing = getProvinceById(id);

        if (!existing.getCode().equals(payload.getCode())
                && provinceRepository.existsByCode(payload.getCode())) {
            throw new DuplicateResourceException("Province code already exists.");
        }

        existing.setName(payload.getName());
        existing.setCode(payload.getCode());
        return provinceRepository.save(existing);
    }

    public void deleteProvince(Long id) {
        if (!provinceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Province not found");
        }
        provinceRepository.deleteById(id);
    }

    // District CRUD
    public District createDistrict(Long provinceId, District district) {
        Province province = getProvinceById(provinceId);
        if (districtRepository.existsByNameAndProvinceId(district.getName(), provinceId)) {
            throw new DuplicateResourceException("District already exists in this province.");
        }
        district.setProvince(province);
        return districtRepository.save(district);
    }

    public List<District> getDistrictsByProvince(Long provinceId) {
        if (!provinceRepository.existsById(provinceId)) {
            throw new ResourceNotFoundException("Province not found");
        }
        return districtRepository.findByProvinceId(provinceId);
    }

    public District getDistrictById(Long id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("District not found"));
    }

    public District updateDistrict(Long id, District payload) {
        District existing = getDistrictById(id);
        Long provinceId = payload.getProvince() != null ? payload.getProvince().getId() : null;

        if (provinceId != null && !provinceRepository.existsById(provinceId)) {
            throw new ResourceNotFoundException("Province not found");
        }

        if (provinceId != null
                && (existing.getProvince() == null || !existing.getProvince().getId().equals(provinceId))
                && districtRepository.existsByNameAndProvinceId(payload.getName(), provinceId)) {
            throw new DuplicateResourceException("District already exists in this province.");
        }

        existing.setName(payload.getName());
        if (provinceId != null) {
            existing.setProvince(getProvinceById(provinceId));
        }
        return districtRepository.save(existing);
    }

    public void deleteDistrict(Long id) {
        if (!districtRepository.existsById(id)) {
            throw new ResourceNotFoundException("District not found");
        }
        districtRepository.deleteById(id);
    }

    // Sector CRUD
    public Sector createSector(Long districtId, Sector sector) {
        District district = getDistrictById(districtId);
        if (sectorRepository.existsByNameAndDistrictId(sector.getName(), districtId)) {
            throw new DuplicateResourceException("Sector already exists in this district.");
        }
        sector.setDistrict(district);
        return sectorRepository.save(sector);
    }

    public List<Sector> getSectorsByDistrict(Long districtId) {
        if (!districtRepository.existsById(districtId)) {
            throw new ResourceNotFoundException("District not found");
        }
        return sectorRepository.findByDistrictId(districtId);
    }

    public Sector getSectorById(Long id) {
        return sectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sector not found"));
    }

    public Sector updateSector(Long id, Sector payload) {
        Sector existing = getSectorById(id);
        Long districtId = payload.getDistrict() != null ? payload.getDistrict().getId() : null;

        if (districtId != null && !districtRepository.existsById(districtId)) {
            throw new ResourceNotFoundException("District not found");
        }

        if (districtId != null
                && (existing.getDistrict() == null || !existing.getDistrict().getId().equals(districtId))
                && sectorRepository.existsByNameAndDistrictId(payload.getName(), districtId)) {
            throw new DuplicateResourceException("Sector already exists in this district.");
        }

        existing.setName(payload.getName());
        if (districtId != null) {
            existing.setDistrict(getDistrictById(districtId));
        }
        return sectorRepository.save(existing);
    }

    public void deleteSector(Long id) {
        if (!sectorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sector not found");
        }
        sectorRepository.deleteById(id);
    }

    // Village CRUD
    public Village createVillage(Long sectorId, Village village) {
        Sector sector = getSectorById(sectorId);
        if (villageRepository.existsByNameAndSectorId(village.getName(), sectorId)) {
            throw new DuplicateResourceException("Village already exists in this sector.");
        }
        village.setSector(sector);
        return villageRepository.save(village);
    }

    public List<Village> getVillagesBySector(Long sectorId) {
        if (!sectorRepository.existsById(sectorId)) {
            throw new ResourceNotFoundException("Sector not found");
        }
        return villageRepository.findBySectorId(sectorId);
    }

    public Village getVillageById(Long id) {
        return villageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found"));
    }

    public Village updateVillage(Long id, Village payload) {
        Village existing = getVillageById(id);
        Long sectorId = payload.getSector() != null ? payload.getSector().getId() : null;

        if (sectorId != null && !sectorRepository.existsById(sectorId)) {
            throw new ResourceNotFoundException("Sector not found");
        }

        if (sectorId != null
                && (existing.getSector() == null || !existing.getSector().getId().equals(sectorId))
                && villageRepository.existsByNameAndSectorId(payload.getName(), sectorId)) {
            throw new DuplicateResourceException("Village already exists in this sector.");
        }

        existing.setName(payload.getName());
        if (sectorId != null) {
            existing.setSector(getSectorById(sectorId));
        }
        return villageRepository.save(existing);
    }

    public void deleteVillage(Long id) {
        if (!villageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Village not found");
        }
        villageRepository.deleteById(id);
    }

    // Hierarchy: provinces with districts, sectors, villages
    @Transactional(readOnly = true)
    public List<Province> getHierarchy() {
        return provinceRepository.findAll();
    }
}
