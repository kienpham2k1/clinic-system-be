package org.clinic.doctor_service.controller;

import org.clinic.common_service_web.constant.PageConstant;
import org.clinic.doctor_service.dto.request.SpecializationRequest;
import org.clinic.doctor_service.dto.response.SpecializationResponse;
import org.clinic.doctor_service.service.DoctorService;
import org.clinic.doctor_service.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specializations")
public class SpecializationController {
    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<Page<SpecializationResponse>> getSpecializationByPage(
            @RequestParam(name = "pageNo", defaultValue = PageConstant.PAGE_START) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstant.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstant.PAGE_ORDER_BY) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = PageConstant.PAGE_ORDER_DIRECTION) String sortDirection
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(specializationService.getSpecializationByPage(pageable));
    }

    @GetMapping("/{specializationId}")
    public ResponseEntity<SpecializationResponse> getSpecializationById(@PathVariable(name = "specializationId") UUID specializationId) {
        return ResponseEntity.ok(specializationService.getSpecializationById(specializationId));
    }

    @PostMapping
    public ResponseEntity<SpecializationResponse> insertSpecialization(@RequestBody SpecializationRequest specializationRequest) {
        return ResponseEntity.ok(specializationService.insertSpecialization(specializationRequest));
    }

    @PutMapping("/{specializationId}")
    public ResponseEntity<SpecializationResponse> updateSpecialization(@PathVariable(name = "specializationId") UUID specializationId,
                                                   @RequestBody SpecializationRequest specializationRequest) {
        return ResponseEntity.ok(specializationService.updateSpecialization(specializationId, specializationRequest));
    }

    @DeleteMapping("/{specializationId}")
    public ResponseEntity<SpecializationResponse> deleteSpecialization(@PathVariable(name = "specializationId") UUID specializationId) {
        return ResponseEntity.ok(specializationService.deleteSpecialization(specializationId));
    }
}
