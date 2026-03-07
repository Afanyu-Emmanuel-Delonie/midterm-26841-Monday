package com.realestate.brokerage_crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.brokerage_crm.model.Client;
import com.realestate.brokerage_crm.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

     public Client createClient(Client client) {
        clientRepository.save(client);
        return "Client";
    }
