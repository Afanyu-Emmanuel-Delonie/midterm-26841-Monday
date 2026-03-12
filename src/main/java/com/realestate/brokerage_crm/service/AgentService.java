package com.realestate.brokerage_crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.realestate.brokerage_crm.exception.DuplicateResourceException;
import com.realestate.brokerage_crm.exception.ResourceNotFoundException;
import com.realestate.brokerage_crm.model.Agent;
import com.realestate.brokerage_crm.model.Village;
import com.realestate.brokerage_crm.repository.AgentRepository;
import com.realestate.brokerage_crm.repository.VillageRepository;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private VillageRepository villageRepository;

    // Create
    public Agent createAgent(Agent agent) {
        if (agentRepository.existsByEmail(agent.getEmail())) {
            throw new DuplicateResourceException("Email already in use.");
        }
        attachVillage(agent);
        return agentRepository.save(agent);
    }

    // Read all
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    // Read all (paged)
    public Page<Agent> getAllAgents(Pageable pageable) {
        return agentRepository.findAll(pageable);
    }

    // Read one
    public Agent getAgentById(Long id) {
        return agentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));
    }

    public List<Agent> getAgentsByProvince(String code, String name) {
        if ((code == null || code.isBlank()) && (name == null || name.isBlank())) {
            throw new ResourceNotFoundException("Province code or name is required");
        }
        return agentRepository.findByProvinceCodeOrName(
                (code == null || code.isBlank()) ? null : code,
                (name == null || name.isBlank()) ? null : name);
    }

    // Update
    public Agent updateAgent(Long id, Agent payload) {
        Agent existing = agentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));

        if (!existing.getEmail().equals(payload.getEmail())
                && agentRepository.existsByEmail(payload.getEmail())) {
            throw new DuplicateResourceException("Email already in use.");
        }

        existing.setName(payload.getName());
        existing.setEmail(payload.getEmail());
        existing.setProfile(payload.getProfile());
        if (payload.getVillage() != null) {
            existing.setVillage(attachVillage(payload));
        }
        return agentRepository.save(existing);
    }

    // Delete
    public void deleteAgent(Long id) {
        if (!agentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agent not found");
        }
        agentRepository.deleteById(id);
    }

    private Village attachVillage(Agent agent) {
        if (agent.getVillage() == null || agent.getVillage().getId() == null) {
            throw new ResourceNotFoundException("Village is required");
        }
        Village village = villageRepository.findById(agent.getVillage().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Village not found"));
        agent.setVillage(village);
        return village;
    }
}
