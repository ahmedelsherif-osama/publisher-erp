package com.ahmed.publisher.erp.user.controller;

import com.ahmed.publisher.erp.user.dto.UserDto;
import com.ahmed.publisher.erp.user.entity.Role;
import com.ahmed.publisher.erp.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Users", description = "User management APIs")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // =====================
    // Assign role
    // =====================

    @Operation(summary = "Assign a role to a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role assigned successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PatchMapping("/{userId}/role")
    public UserDto assignRole(
            @PathVariable UUID userId,
            @RequestParam Role role
    ) {
        log.info("Received request to assign role={} to userId={}", role, userId);

        UserDto updatedUser = userService.updateUserRole(userId, role);

        log.info("Role updated successfully for userId={} (newRole={})",
                userId, role);

        return updatedUser;
    }

    // =====================
    // Get all users
    // =====================

    @Operation(summary = "Get all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public List<UserDto> getAllUsers() {
        log.debug("Received request to fetch all users");

        List<UserDto> users = userService.getAllUsers();

        log.debug("Returning {} users", users.size());
        return users;
    }
}
