package com.ahmed.publisher.erp.authentication;

import com.ahmed.publisher.erp.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class JwtTokenService implements TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private final long expirationMs = 1000 * 60 * 60 * 24; // 24h

    @Override
    public Optional<String> createToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        return Optional.of(token);
    }



    @Override
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<UUID> extractUserId(String token) {
        try {
            return Optional.of(UUID.fromString(getClaims(token).getSubject()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public String getType() {
        return "JWT";
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
