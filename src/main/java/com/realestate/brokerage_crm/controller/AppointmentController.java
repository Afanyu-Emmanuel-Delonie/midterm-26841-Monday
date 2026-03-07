package com.realestate.brokerage_crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.brokerage_crm.model.Appointment;
import com.realestate.brokerage_crm.service.AppointmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Create
    @PostMapping
    @Operation(summary = "Create appointment")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = "{\n  \"type\": \"VIEWING\",\n  \"status\": \"SCHEDULED\",\n  \"scheduledAt\": \"2026-03-10T14:30:00\",\n  \"notes\": \"Client wants to see the balcony\",\n  \"property\": { \"id\": 1 },\n  \"agent\": { \"id\": 1 },\n  \"client\": { \"id\": 1 }\n}")))
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment created = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(201).body(created);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointment));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    // Filter by agent, client, or property
    @GetMapping("/search")
    public ResponseEntity<List<Appointment>> searchAppointments(
            @RequestParam(required = false) Long agentId,
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long propertyId) {
        if (agentId != null) {
            return ResponseEntity.ok(appointmentService.getAppointmentsByAgent(agentId));
        }
        if (clientId != null) {
            return ResponseEntity.ok(appointmentService.getAppointmentsByClient(clientId));
        }
        if (propertyId != null) {
            return ResponseEntity.ok(appointmentService.getAppointmentsByProperty(propertyId));
        }
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
}
