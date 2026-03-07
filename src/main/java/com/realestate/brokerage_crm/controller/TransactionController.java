package com.realestate.brokerage_crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.brokerage_crm.model.Transaction;
import com.realestate.brokerage_crm.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/sell")
    @Operation(summary = "Sell a property (distribute income)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = "{\n  \"propertyId\": 1,\n  \"salePrice\": 100000\n}")))
    public ResponseEntity<TransactionResponse> sellProperty(@RequestBody SellRequest request) {
        Transaction tx = transactionService.sellProperty(request.getPropertyId(), request.getSalePrice());
        TransactionResponse response = new TransactionResponse(
                tx.getId(),
                tx.getProperty().getId(),
                tx.getAgent().getId(),
                tx.getSalePrice(),
                tx.getAgentCommission(),
                tx.getCompanyRevenue(),
                tx.getCreatedAt()
        );
        return ResponseEntity.status(201).body(response);
    }

    public static class SellRequest {
        private Long propertyId;
        private Double salePrice;

        public Long getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(Long propertyId) {
            this.propertyId = propertyId;
        }

        public Double getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(Double salePrice) {
            this.salePrice = salePrice;
        }
    }

    public static class TransactionResponse {
        private Long id;
        private Long propertyId;
        private Long agentId;
        private Double salePrice;
        private Double agentCommission;
        private Double companyRevenue;
        private java.time.LocalDateTime createdAt;

        public TransactionResponse(
                Long id,
                Long propertyId,
                Long agentId,
                Double salePrice,
                Double agentCommission,
                Double companyRevenue,
                java.time.LocalDateTime createdAt) {
            this.id = id;
            this.propertyId = propertyId;
            this.agentId = agentId;
            this.salePrice = salePrice;
            this.agentCommission = agentCommission;
            this.companyRevenue = companyRevenue;
            this.createdAt = createdAt;
        }

        public Long getId() {
            return id;
        }

        public Long getPropertyId() {
            return propertyId;
        }

        public Long getAgentId() {
            return agentId;
        }

        public Double getSalePrice() {
            return salePrice;
        }

        public Double getAgentCommission() {
            return agentCommission;
        }

        public Double getCompanyRevenue() {
            return companyRevenue;
        }

        public java.time.LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
