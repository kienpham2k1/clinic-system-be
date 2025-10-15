package org.clinic.patient_service.repository;

import org.clinic.patient_service.model.sql.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, UUID> {
    boolean existsByEmail(String email);
}
