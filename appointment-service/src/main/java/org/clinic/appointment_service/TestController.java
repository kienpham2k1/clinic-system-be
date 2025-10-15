package org.clinic.appointment_service;

import org.clinic.appointment_service.client.DoctorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private DoctorClient doctorClient;

    @GetMapping
    public String test() {
        return doctorClient.test();
    }
}
