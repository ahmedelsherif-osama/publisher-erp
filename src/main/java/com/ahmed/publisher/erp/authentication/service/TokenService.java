package com.ahmed.publisher.erp.authentication.service;

import com.ahmed.publisher.erp.user.entity.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Generic token service interface.
 * Supports JWT, opaque DB tokens, OAuth, ephemeral tokens.
 */
public interface TokenService {

    /**
     * Create a token for a user.
     * Optional.empty() if token creation is not supported (e.g., pure OAuth access token).
     */
    Optional<String> createToken(User user);

    /**
     * Validate a token. Must always be implemented.
     */
    boolean validate(String token);

    /**
     * Extract user ID from token if possible.
     * Optional.empty() if userId cannot be extracted (OAuth / ephemeral tokens may require external lookup)
     */
    Optional<UUID> extractUserId(String token);

    /**
     * Get type of token strategy: "JWT", "Opaque", "OAuth", etc.
     */
    String getType();
}
