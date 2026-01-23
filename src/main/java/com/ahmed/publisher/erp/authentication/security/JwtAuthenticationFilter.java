package com.ahmed.publisher.erp.authentication.security;

import com.ahmed.publisher.erp.authentication.service.TokenService;
import com.ahmed.publisher.erp.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final TokenService tokenService;
    private final UserService userService;

    public JwtAuthenticationFilter(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            log.debug("No JWT token found in request for {}", request.getRequestURI());
            String token = authHeader.substring(7);

            if (tokenService.validate(token)) {
                tokenService.extractUserId(token)
                        .map(userService::findById)
                        .map(SecurityUser::new)   // 👈 adapter here
                        .ifPresent(securityUser -> {

                            UsernamePasswordAuthenticationToken auth =
                                    new UsernamePasswordAuthenticationToken(
                                            securityUser,
                                            null,
                                            securityUser.getAuthorities()
                                    );

                            auth.setDetails(
                                    new WebAuthenticationDetailsSource()
                                            .buildDetails(request)
                            );

                            SecurityContextHolder.getContext()
                                    .setAuthentication(auth);
                            log.debug("Authenticated user {} for request {}", securityUser.getUsername(), request.getRequestURI());
                        });

            } else {
                log.warn("Invalid JWT token for request {}", request.getRequestURI());
            }
        }
        else {
            log.debug("JWT token found, processing authentication for request {}", request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }
}
