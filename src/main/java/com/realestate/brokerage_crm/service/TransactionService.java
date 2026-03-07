package com.realestate.brokerage_crm.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realestate.brokerage_crm.exception.ResourceNotFoundException;
import com.realestate.brokerage_crm.model.Agent;
import com.realestate.brokerage_crm.model.CompanyRevenue;
import com.realestate.brokerage_crm.model.Property;
import com.realestate.brokerage_crm.model.Transaction;
import com.realestate.brokerage_crm.repository.AgentRepository;
import com.realestate.brokerage_crm.repository.CompanyRevenueRepository;
import com.realestate.brokerage_crm.repository.PropertyRepository;
import com.realestate.brokerage_crm.repository.TransactionRepository;

@Service
public class TransactionService {

    private static final double AGENT_RATE = 0.05;
    private static final double COMPANY_RATE = 0.02;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CompanyRevenueRepository companyRevenueRepository;

    @Transactional
    public Transaction sellProperty(Long propertyId, Double salePrice) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));

        Agent agent = property.getAgent();
        if (agent == null) {
            throw new ResourceNotFoundException("Agent not found for property");
        }

        double agentCommission = salePrice * AGENT_RATE;
        double companyRevenue = salePrice * COMPANY_RATE;

        property.setStatus("SOLD");
        propertyRepository.save(property);

        Double currentEarnings = agent.getTotalEarnings() == null ? 0.0 : agent.getTotalEarnings();
        agent.setTotalEarnings(currentEarnings + agentCommission);
        agentRepository.save(agent);

        CompanyRevenue revenue = companyRevenueRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> companyRevenueRepository.save(new CompanyRevenue()));
        Double currentRevenue = revenue.getTotalRevenue() == null ? 0.0 : revenue.getTotalRevenue();
        revenue.setTotalRevenue(currentRevenue + companyRevenue);
        companyRevenueRepository.save(revenue);

        Transaction tx = new Transaction();
        tx.setProperty(property);
        tx.setAgent(agent);
        tx.setSalePrice(salePrice);
        tx.setAgentCommission(agentCommission);
        tx.setCompanyRevenue(companyRevenue);
        tx.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(tx);
    }
}
