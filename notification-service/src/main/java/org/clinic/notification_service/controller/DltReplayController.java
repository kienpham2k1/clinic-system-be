package org.clinic.notification_service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.clinic.notification_service.service.kafka.DltReplayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/dlt")
@AllArgsConstructor
public class DltReplayController {
    private final DltReplayService dltReplayService;

    @PostMapping("/replay/{dtl-id}")
    public ResponseEntity<String> replay(@PathVariable(name = "dtl-id") UUID id) {
        dltReplayService.replay(id);
        return ResponseEntity.ok("Replayed DLT id=" + id);
    }
}
