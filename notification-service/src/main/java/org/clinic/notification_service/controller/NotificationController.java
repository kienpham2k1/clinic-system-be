package org.clinic.notification_service.controller;

import lombok.RequiredArgsConstructor;
import org.clinic.common_service_web.constant.PageConstant;
import org.clinic.notification_service.dto.request.NotificationRequest;
import org.clinic.notification_service.dto.response.NotificationResponse;
import org.clinic.notification_service.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getNotificationByPage(
            @RequestParam(name = "pageNo", defaultValue = PageConstant.PAGE_START) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstant.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstant.PAGE_ORDER_BY) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = PageConstant.PAGE_ORDER_DIRECTION) String sortDirection
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(notificationService.getNotificationByPage(pageable));
    }

    @PostMapping(value = "/get-list")
    public ResponseEntity<List<NotificationResponse>> getNotificationsList(@RequestBody List<UUID> notificationIds) {
        return ResponseEntity.ok(notificationService.getNotificationsList(notificationIds));
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponse> getRoleById(@PathVariable(name = "notificationId") UUID notificationId) {
        return ResponseEntity.ok(notificationService.getNotificationById(notificationId));
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> insertNotification(@RequestBody NotificationRequest notificationRequest) {
        return ResponseEntity.ok(notificationService.insertNotification(notificationRequest));
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable(name = "notificationId") UUID notificationId,
                                                                   @RequestBody NotificationRequest notificationRequest) {
        return ResponseEntity.ok(notificationService.updateNotification(notificationId, notificationRequest));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<NotificationResponse> deleteNotification(@PathVariable(name = "notificationId") UUID notificationId) {
        return ResponseEntity.ok(notificationService.deleteNotification(notificationId));
    }

    @PutMapping("/{notificationId}/mark-as-read")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable(name = "notificationId") UUID notificationId) {
        return ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }
}