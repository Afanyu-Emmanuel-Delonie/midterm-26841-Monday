package com.realestate.brokerage_crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.brokerage_crm.exception.DuplicateResourceException;
import com.realestate.brokerage_crm.exception.ResourceNotFoundException;
import com.realestate.brokerage_crm.model.Agent;
import com.realestate.brokerage_crm.model.Client;
import com.realestate.brokerage_crm.repository.AgentRepository;
import com.realestate.brokerage_crm.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AgentRepository agentRepository;

    // Create
    public Client createClient(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new DuplicateResourceException("Email already in use.");
        }
        attachAgents(client);
        return clientRepository.save(client);
    }

    // Read all
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Read one
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    // Update
    public Client updateClient(Long id, Client payload) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        if (!existing.getEmail().equals(payload.getEmail())
                && clientRepository.existsByEmail(payload.getEmail())) {
            throw new DuplicateResourceException("Email already in use.");
        }

        existing.setName(payload.getName());
        existing.setEmail(payload.getEmail());
        existing.setPhone(payload.getPhone());
        if (payload.getAgents() != null) {
            existing.setAgents(resolveAgents(payload.getAgents()));
        }

        return clientRepository.save(existing);
    }

    // Delete
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client not found");
        }
        clientRepository.deleteById(id);
    }

    private void attachAgents(Client client) {
        if (client.getAgents() != null) {
            client.setAgents(resolveAgents(client.getAgents()));
        }
    }

    private List<Agent> resolveAgents(List<Agent> agents) {
        return agents.stream()
                .map(a -> agentRepository.findById(a.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Agent not found")))
                .toList();
    }
}
