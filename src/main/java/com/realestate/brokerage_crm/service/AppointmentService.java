package com.realestate.brokerage_crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realestate.brokerage_crm.exception.ResourceNotFoundException;
import com.realestate.brokerage_crm.model.Agent;
import com.realestate.brokerage_crm.model.Appointment;
import com.realestate.brokerage_crm.model.Client;
import com.realestate.brokerage_crm.model.Property;
import com.realestate.brokerage_crm.repository.AgentRepository;
import com.realestate.brokerage_crm.repository.AppointmentRepository;
import com.realestate.brokerage_crm.repository.ClientRepository;
import com.realestate.brokerage_crm.repository.PropertyRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    // Create
    public Appointment createAppointment(Appointment appointment) {
        attachRelations(appointment);
        return appointmentRepository.save(appointment);
    }

    // Read all
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Read one
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
    }

    // Update
    public Appointment updateAppointment(Long id, Appointment payload) {
        Appointment existing = getAppointmentById(id);
        existing.setType(payload.getType());
        existing.setStatus(payload.getStatus());
        existing.setScheduledAt(payload.getScheduledAt());
        existing.setNotes(payload.getNotes());

        if (payload.getAgent() != null) {
            existing.setAgent(resolveAgent(payload.getAgent().getId()));
        }
        if (payload.getClient() != null) {
            existing.setClient(resolveClient(payload.getClient().getId()));
        }
        if (payload.getProperty() != null) {
            existing.setProperty(resolveProperty(payload.getProperty().getId()));
        }

        return appointmentRepository.save(existing);
    }

    // Delete
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsByAgent(Long agentId) {
        if (!agentRepository.existsById(agentId)) {
            throw new ResourceNotFoundException("Agent not found");
        }
        return appointmentRepository.findByAgentId(agentId);
    }

    public List<Appointment> getAppointmentsByClient(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client not found");
        }
        return appointmentRepository.findByClientId(clientId);
    }

    public List<Appointment> getAppointmentsByProperty(Long propertyId) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new ResourceNotFoundException("Property not found");
        }
        return appointmentRepository.findByPropertyId(propertyId);
    }

    private void attachRelations(Appointment appointment) {
        appointment.setAgent(resolveAgent(appointment.getAgent() != null ? appointment.getAgent().getId() : null));
        appointment.setClient(resolveClient(appointment.getClient() != null ? appointment.getClient().getId() : null));
        appointment.setProperty(resolveProperty(appointment.getProperty() != null ? appointment.getProperty().getId() : null));
    }

    private Agent resolveAgent(Long agentId) {
        if (agentId == null) {
            throw new ResourceNotFoundException("Agent is required");
        }
        return agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));
    }

    private Client resolveClient(Long clientId) {
        if (clientId == null) {
            throw new ResourceNotFoundException("Client is required");
        }
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    private Property resolveProperty(Long propertyId) {
        if (propertyId == null) {
            throw new ResourceNotFoundException("Property is required");
        }
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
    }
}
