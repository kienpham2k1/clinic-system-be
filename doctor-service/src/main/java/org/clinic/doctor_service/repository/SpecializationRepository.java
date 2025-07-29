package org.clinic.doctor_service.repository;

import org.clinic.doctor_service.model.sql.DepartmentEntity;
import org.clinic.doctor_service.model.sql.SpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecializationRepository extends JpaRepository<SpecializationEntity, UUID> {
}
