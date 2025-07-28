package org.clinic.doctorservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    @GetMapping
    public ResponseEntity<String> findAll() {
        return new ResponseEntity<>("asdad", HttpStatus.OK);
    }
}
