package com.realestate.brokerage_crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.brokerage_crm.exception.ResourceNotFoundException;
import com.realestate.brokerage_crm.model.Agent;
import com.realestate.brokerage_crm.model.Property;
import com.realestate.brokerage_crm.model.Village;
import com.realestate.brokerage_crm.repository.AgentRepository;
import com.realestate.brokerage_crm.repository.PropertyRepository;
import com.realestate.brokerage_crm.repository.VillageRepository;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private VillageRepository villageRepository;

    // Create
    public Property createProperty(Property property) {
        attachAgentAndVillage(property);
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

        if (payload.getVillage() != null) {
            Village village = resolveVillage(payload.getVillage().getId());
            existing.setVillage(village);
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

    public List<Property> getPropertiesByVillage(Long villageId) {
        if (!villageRepository.existsById(villageId)) {
            throw new ResourceNotFoundException("Village not found");
        }
        return propertyRepository.findByVillageId(villageId);
    }

    public List<Property> getPropertiesByAgent(Long agentId) {
        if (!agentRepository.existsById(agentId)) {
            throw new ResourceNotFoundException("Agent not found");
        }
        return propertyRepository.findByAgentId(agentId);
    }

    private void attachAgentAndVillage(Property property) {
        property.setAgent(resolveAgent(property.getAgent() != null ? property.getAgent().getId() : null));
        property.setVillage(resolveVillage(property.getVillage() != null ? property.getVillage().getId() : null));
    }

    private Agent resolveAgent(Long agentId) {
        if (agentId == null) {
            throw new ResourceNotFoundException("Agent is required");
        }
        return agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));
    }

    private Village resolveVillage(Long villageId) {
        if (villageId == null) {
            throw new ResourceNotFoundException("Village is required");
        }
        return villageRepository.findById(villageId)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found"));
    }
}
