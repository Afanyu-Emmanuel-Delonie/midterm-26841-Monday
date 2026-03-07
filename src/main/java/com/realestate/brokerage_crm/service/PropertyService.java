package com.realestate.brokerage_crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.brokerage_crm.exception.ResourceNotFoundException;
import com.realestate.brokerage_crm.model.Agent;
import com.realestate.brokerage_crm.model.City;
import com.realestate.brokerage_crm.model.Property;
import com.realestate.brokerage_crm.repository.AgentRepository;
import com.realestate.brokerage_crm.repository.CityRepository;
import com.realestate.brokerage_crm.repository.PropertyRepository;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CityRepository cityRepository;

    // Create
    public Property createProperty(Property property) {
        attachAgentAndCity(property);
        return propertyRepository.save(property);
    }

    // Read all
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // Read one
    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
    }

    // Update
    public Property updateProperty(Long id, Property payload) {
        Property existing = getPropertyById(id);
        existing.setTitle(payload.getTitle());
        existing.setPrice(payload.getPrice());
        existing.setStatus(payload.getStatus());

        if (payload.getAgent() != null) {
            Agent agent = resolveAgent(payload.getAgent().getId());
            existing.setAgent(agent);
        }

        if (payload.getCity() != null) {
            City city = resolveCity(payload.getCity().getId());
            existing.setCity(city);
        }

        return propertyRepository.save(existing);
    }

    // Delete
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Property not found");
        }
        propertyRepository.deleteById(id);
    }

    public List<Property> getPropertiesByCity(Long cityId) {
        if (!cityRepository.existsById(cityId)) {
            throw new ResourceNotFoundException("City not found");
        }
        return propertyRepository.findByCityId(cityId);
    }

    public List<Property> getPropertiesByAgent(Long agentId) {
        if (!agentRepository.existsById(agentId)) {
            throw new ResourceNotFoundException("Agent not found");
        }
        return propertyRepository.findByAgentId(agentId);
    }

    private void attachAgentAndCity(Property property) {
        property.setAgent(resolveAgent(property.getAgent() != null ? property.getAgent().getId() : null));
        property.setCity(resolveCity(property.getCity() != null ? property.getCity().getId() : null));
    }

    private Agent resolveAgent(Long agentId) {
        if (agentId == null) {
            throw new ResourceNotFoundException("Agent is required");
        }
        return agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));
    }

    private City resolveCity(Long cityId) {
        if (cityId == null) {
            throw new ResourceNotFoundException("City is required");
        }
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));
    }
}
