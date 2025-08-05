package org.clinic.appointment_service.controller;

import org.clinic.appointment_service.dto.request.AppointmentRequest;
import org.clinic.appointment_service.dto.response.AppointmentResponse;
import org.clinic.appointment_service.service.AppointmentService;
import org.clinic.common_service_web.constant.PageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> getRoleByPage(
            @RequestParam(name = "pageNo", defaultValue = PageConstant.PAGE_START) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstant.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstant.PAGE_ORDER_BY) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = PageConstant.PAGE_ORDER_DIRECTION) String sortDirection
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(appointmentService.getAppointmentByPage(pageable));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> getRoleById(@PathVariable(name = "appointmentId") UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(appointmentId));
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> insertDoctor(@RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity.ok(appointmentService.insertAppointment(appointmentRequest));
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> updateDoctor(@PathVariable(name = "appointmentId") UUID appointmentId,
                                                            @RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity.ok(appointmentService.updateAppointment(appointmentId, appointmentRequest));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> deleteDoctor(@PathVariable(name = "appointmentId") UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.deleteAppointment(appointmentId));
    }
}
