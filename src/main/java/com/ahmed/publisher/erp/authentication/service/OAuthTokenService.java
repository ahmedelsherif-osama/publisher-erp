package com.ahmed.publisher.erp.authentication.service;

import com.ahmed.publisher.erp.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OAuthTokenService implements TokenService {
    private static final Logger log = LoggerFactory.getLogger(OAuthTokenService.class);
    @Override
    public Optional<String> createToken(User user) {
        log.debug("OAuth token creation requested but not supported locally, userId={}", user.getId());
        return Optional.empty(); // cannot create token locally
    }

    @Override
    public boolean validate(String token) {
        log.debug("Validating OAuth token via provider");
        // call OAuth provider introspection endpoint
        return true; // pseudo-code
    }

    @Override
    public Optional<UUID> extractUserId(String token) {
        log.debug("Extracting userId from OAuth token via provider");
        // fetch user info from provider
        return Optional.of(UUID.randomUUID()); // pseudo-code
    }

    @Override
    public String getType() {
        return "OAuth";
    }
}
