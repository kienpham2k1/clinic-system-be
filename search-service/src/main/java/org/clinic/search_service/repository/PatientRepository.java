package org.clinic.search_service.repository;

import org.clinic.search_service.model.Patient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends ElasticsearchRepository<Patient, UUID> {
}
