package com.ahmed.publisher.erp.user;

import com.ahmed.publisher.erp.authentication.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrentUserService {

    public UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        // Assuming principal is the UUID as a String
         User user = ((SecurityUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getDomainUser();
        return user.getId();
    }
}
