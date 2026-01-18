package com.ahmed.publisher.erp.user.service;


import com.ahmed.publisher.erp.user.dto.PatchUserRequest;
import com.ahmed.publisher.erp.user.dto.UpdateUserRequest;
import com.ahmed.publisher.erp.user.dto.UserDto;
import com.ahmed.publisher.erp.user.dto.UserRegistrationRequest;
import com.ahmed.publisher.erp.user.entity.Role;
import com.ahmed.publisher.erp.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(UUID id);
    User create(UserRegistrationRequest request);
    UserDto updateFully(UUID id, UpdateUserRequest request);
    UserDto updatePartially(UUID id, PatchUserRequest request);
    void delete(UUID id);
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    User findById(UUID userId);
    UserDto updateUserRole(UUID userId, Role role);
}
