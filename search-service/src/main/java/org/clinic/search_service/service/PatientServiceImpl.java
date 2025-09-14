package org.clinic.search_service.service;

import lombok.AllArgsConstructor;
import org.clinic.search_service.model.Patient;
import org.clinic.search_service.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Override
    public Iterable<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(int id) {
        return null;
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void update(UUID id, Patient patient) {
        Patient p = patientRepository.findById(patient.getId()).orElse(null);
        patientRepository.save(patient);
    }

    @Override
    public void delete(UUID id) {
        patientRepository.deleteById(id);
    }
}
