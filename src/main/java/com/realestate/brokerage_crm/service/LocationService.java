package com.realestate.brokerage_crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realestate.brokerage_crm.exception.DuplicateResourceException;
import com.realestate.brokerage_crm.exception.ResourceNotFoundException;
import com.realestate.brokerage_crm.model.City;
import com.realestate.brokerage_crm.model.Province;
import com.realestate.brokerage_crm.repository.CityRepository;
import com.realestate.brokerage_crm.repository.ProvinceRepository;

@Service
public class LocationService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private CityRepository cityRepository;

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

    // City CRUD
    public City createCity(Long provinceId, City city) {
        Province province = getProvinceById(provinceId);
        if (cityRepository.existsByNameAndProvinceId(city.getName(), provinceId)) {
            throw new DuplicateResourceException("City already exists in this province.");
        }
        city.setProvince(province);
        return cityRepository.save(city);
    }

    public List<City> getCitiesByProvince(Long provinceId) {
        if (!provinceRepository.existsById(provinceId)) {
            throw new ResourceNotFoundException("Province not found");
        }
        return cityRepository.findByProvinceId(provinceId);
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));
    }

    public City updateCity(Long id, City payload) {
        City existing = getCityById(id);
        Long provinceId = payload.getProvince() != null ? payload.getProvince().getId() : null;

        if (provinceId != null && !provinceRepository.existsById(provinceId)) {
            throw new ResourceNotFoundException("Province not found");
        }

        if (provinceId != null
                && (existing.getProvince() == null || !existing.getProvince().getId().equals(provinceId))
                && cityRepository.existsByNameAndProvinceId(payload.getName(), provinceId)) {
            throw new DuplicateResourceException("City already exists in this province.");
        }

        existing.setName(payload.getName());
        if (provinceId != null) {
            existing.setProvince(getProvinceById(provinceId));
        }
        return cityRepository.save(existing);
    }

    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("City not found");
        }
        cityRepository.deleteById(id);
    }

    // Hierarchy: provinces with cities
    @Transactional(readOnly = true)
    public List<Province> getHierarchy() {
        return provinceRepository.findAll();
    }
}
