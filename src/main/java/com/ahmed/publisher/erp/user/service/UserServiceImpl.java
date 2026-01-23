package com.ahmed.publisher.erp.user.service;

import com.ahmed.publisher.erp.exceptions.http.ResourceNotFoundException;
import com.ahmed.publisher.erp.user.repository.UserRepository;
import com.ahmed.publisher.erp.user.dto.PatchUserRequest;
import com.ahmed.publisher.erp.user.dto.UpdateUserRequest;
import com.ahmed.publisher.erp.user.dto.UserDto;
import com.ahmed.publisher.erp.user.dto.UserRegistrationRequest;
import com.ahmed.publisher.erp.user.entity.Role;
import com.ahmed.publisher.erp.user.entity.User;
import com.ahmed.publisher.erp.user.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.debug("Retrieving all users");
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public UserDto getUserById(UUID id) {
        log.debug("Fetching user id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User id={} not found", id);
                    return new ResourceNotFoundException("User with id " + id + " not found");
                });

        return UserMapper.toDto(user);
    }

    @Override
    public User create(UserRegistrationRequest request) {
        log.info("Creating new user with email={}", request.email());
        User user = UserMapper.toEntity(request);
        return userRepository.save(user);
    }

    @Override
    public UserDto updateFully(UUID id, UpdateUserRequest request) {
        log.info("Fully updating user id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        UserMapper.applyUpdate(user, request);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto updatePartially(UUID id, PatchUserRequest request) {
        log.info("Partially updating user id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        UserMapper.applyPatch(user, request);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting user id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        userRepository.delete(user);
    }

    @Override
    public User findByEmail(String email) {
        log.debug("Looking up user by email");

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email)
                );
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        // ⚠️ intentionally minimal logging here
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with these credentials")
                );
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + userId)
                );
    }

    @Override
    public UserDto updateUserRole(UUID userId, Role role){
        log.info("Updating role for userId={} to {}", userId, role);

        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        user.setRole(role);
        return UserMapper.toDto(userRepository.save(user));
    }
}
