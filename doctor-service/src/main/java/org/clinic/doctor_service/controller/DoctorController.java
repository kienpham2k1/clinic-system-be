package org.clinic.doctor_service.controller;

import org.clinic.common_service_web.constant.PageConstant;
import org.clinic.doctor_service.dto.request.DoctorRequest;
import org.clinic.doctor_service.dto.response.DoctorResponse;
import org.clinic.doctor_service.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<Page<DoctorResponse>> getDoctorByPage(
            @RequestParam(name = "pageNo", defaultValue = PageConstant.PAGE_START) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstant.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstant.PAGE_ORDER_BY) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = PageConstant.PAGE_ORDER_DIRECTION) String sortDirection
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(doctorService.getDoctorByPage(pageable));
    }

    @PostMapping(value = "/get-list")
    public ResponseEntity<List<DoctorResponse>> getDoctorsList(@RequestBody List<UUID> doctorIds) {
        return ResponseEntity.ok(doctorService.getDoctorsList(doctorIds));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> getRoleById(@PathVariable(name = "doctorId") UUID doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorById(doctorId));
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> insertDoctor(@RequestBody DoctorRequest doctorRequest) {
        return ResponseEntity.ok(doctorService.insertDoctor(doctorRequest));
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> updateDoctor(@PathVariable(name = "doctorId") UUID doctorId,
                                                       @RequestBody DoctorRequest doctorRequest) {
        return ResponseEntity.ok(doctorService.updateDoctor(doctorId, doctorRequest));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> deleteDoctor(@PathVariable(name = "doctorId") UUID doctorId) {
        return ResponseEntity.ok(doctorService.deleteDoctor(doctorId));
    }
}
