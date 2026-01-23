package com.ahmed.publisher.erp.user.controller;

import com.ahmed.publisher.erp.user.service.UserService;
import com.ahmed.publisher.erp.user.dto.UserDto;
import com.ahmed.publisher.erp.user.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PatchMapping("/{userId}/role")
    public UserDto assignRole(@PathVariable UUID userId, @RequestParam Role role) {
        log.info("Assigning role={} to userId={}", role, userId);
        return userService.updateUserRole(userId, role);
    }

    @GetMapping
    public List<UserDto> getAllUsers(){
        log.debug("Fetching all users");
        return userService.getAllUsers();
    }
}
