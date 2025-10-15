package org.clinic.search_service.service;

import org.clinic.search_service.model.Patient;

import java.util.UUID;

public interface PatientService {
    Iterable<Patient> findAll();
    Patient findById(int id);
    Patient save(Patient patient);
    void update(UUID id, Patient patient);
    void delete(UUID id);
}
