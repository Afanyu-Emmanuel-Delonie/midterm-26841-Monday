package com.realestate.brokerage_crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

     public Client createClient(Client client) {
        // Add any necessary validation here
        return clientRepository.save(client);
        
}
