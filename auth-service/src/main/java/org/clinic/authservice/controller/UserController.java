package org.clinic.authservice.controller;

import jakarta.validation.Valid;
import org.clinic.authservice.dto.request.UserRegisterRequest;
import org.clinic.authservice.dto.request.UserUpdateRequest;
import org.clinic.authservice.dto.response.UserResponse;
import org.clinic.authservice.service.UserService;
import org.clinic.commonserviceweb.constant.PageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getPatientsPage(
            @RequestParam(name = "pageNo", defaultValue = PageConstant.PAGE_START) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstant.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstant.PAGE_ORDER_BY) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = PageConstant.PAGE_ORDER_DIRECTION) String sortDirection
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(userService.getUserByPage(pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "userId") UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<UserResponse> insertUser(@RequestBody @Valid UserRegisterRequest userDto) {
        return ResponseEntity.ok(userService.insertUser(userDto));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(name = "userId") UUID userId,
                                                   @RequestBody UserUpdateRequest userDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable(name = "userId") UUID userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
