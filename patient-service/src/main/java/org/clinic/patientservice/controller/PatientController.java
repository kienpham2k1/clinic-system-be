package org.clinic.patientservice.controller;

import jakarta.validation.Valid;
import org.clinic.common_service_web.constant.PageConstant;
import org.clinic.patientservice.dto.request.PatientRequestDto;
import org.clinic.patientservice.dto.response.PatientResponseDto;
import org.clinic.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<Page<PatientResponseDto>> getPatientsPage(
            @RequestParam(name = "pageNo", defaultValue = PageConstant.PAGE_START) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstant.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstant.PAGE_ORDER_BY) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = PageConstant.PAGE_ORDER_DIRECTION) String sortDirection
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(patientService.getPatientsPage(pageable));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable(name = "patientId") UUID patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> insertPatient(@RequestBody @Valid PatientRequestDto patientRequestDto) {
        return ResponseEntity.ok(patientService.insertPatient(patientRequestDto));
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable(name = "patientId") UUID patientId,
                                                            @RequestBody PatientRequestDto patientRequestDto) {
        return ResponseEntity.ok(patientService.updatePatient(patientId, patientRequestDto));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<PatientResponseDto> deletePatient(@PathVariable(name = "patientId") UUID patientId) {
        return ResponseEntity.ok(patientService.deletePatient(patientId));
    }
}
