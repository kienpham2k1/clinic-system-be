package org.clinic.search_service.controller;

import lombok.AllArgsConstructor;
import org.clinic.search_service.model.Patient;
import org.clinic.search_service.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@AllArgsConstructor
public class SearchController {
    private final PatientService patientService;

    @GetMapping()
    public Iterable<Patient> getPatients() {
        return patientService.findAll();
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.save(patient));
    }
    @PutMapping("/{id}")
    ResponseEntity<?> updatePatient(@PathVariable(name = "id") UUID id, @RequestBody Patient patient) {
        patientService.update(id, patient);
        return ResponseEntity.ok(patient);
    }
    @DeleteMapping("/{id}")
    private void deletePatient(@PathVariable(name = "id") UUID id) {
        patientService.delete(id);
    }
}
    