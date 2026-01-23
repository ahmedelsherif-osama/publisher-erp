package com.ahmed.publisher.erp.user.service;

import com.ahmed.publisher.erp.authentication.security.SecurityUser;
import com.ahmed.publisher.erp.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrentUserService {

    private static final Logger log = LoggerFactory.getLogger(CurrentUserService.class);

    public UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            log.warn("Attempt to access current user without authentication");
            throw new IllegalStateException("No authenticated user found");
        }

        User user = ((SecurityUser) auth.getPrincipal()).getDomainUser();
        log.debug("Resolved current userId={}", user.getId());

        return user.getId();
    }
}
