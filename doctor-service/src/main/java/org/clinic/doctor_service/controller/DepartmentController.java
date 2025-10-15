package org.clinic.doctor_service.controller;

import org.clinic.common_service_web.constant.PageConstant;
import org.clinic.doctor_service.dto.request.DepartmentRequest;
import org.clinic.doctor_service.dto.response.DepartmentResponse;
import org.clinic.doctor_service.service.DepartmentService;
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
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<Page<DepartmentResponse>> getDepartmentByPage(
            @RequestParam(name = "pageNo", defaultValue = PageConstant.PAGE_START) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstant.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstant.PAGE_ORDER_BY) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = PageConstant.PAGE_ORDER_DIRECTION) String sortDirection
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(departmentService.getDepartmentByPage(pageable));
    }

    @PostMapping(value = "/get-list")
    public ResponseEntity<List<DepartmentResponse>> getDepartmentsList(@RequestBody List<UUID> departmentIds) {
        return ResponseEntity.ok(departmentService.getDepartmentsList(departmentIds));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable(name = "departmentId") UUID departmentId) {
        return ResponseEntity.ok(departmentService.getDepartmentById(departmentId));
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> insertDoctor(@RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.ok(departmentService.insertDepartment(departmentRequest));
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable(name = "departmentId") UUID departmentId,
                                                               @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentId, departmentRequest));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> deleteDoctor(@PathVariable(name = "departmentId") UUID departmentId) {
        return ResponseEntity.ok(departmentService.deleteDepartment(departmentId));
    }
}
