package com.realestate.brokerage_crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.brokerage_crm.model.City;
import com.realestate.brokerage_crm.exception.DuplicateResourceException;
import com.realestate.brokerage_crm.exception.ResourceNotFoundException;
import com.realestate.brokerage_crm.model.Agent;
import com.realestate.brokerage_crm.repository.CityRepository;
import com.realestate.brokerage_crm.repository.AgentRepository;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CityRepository cityRepository;

    // Create
    public Agent createAgent(Agent agent) {
        if (agentRepository.existsByEmail(agent.getEmail())) {
            throw new DuplicateResourceException("Email already in use.");
        }
        attachCity(agent);
        return agentRepository.save(agent);
    }

    // Read all
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    // Read one
    public Agent getAgentById(Long id) {
        return agentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));
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
        if (payload.getCity() != null) {
            existing.setCity(attachCity(payload));
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

    private City attachCity(Agent agent) {
        if (agent.getCity() == null || agent.getCity().getId() == null) {
            return null;
        }
        City city = cityRepository.findById(agent.getCity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));
        agent.setCity(city);
        return city;
    }
}
