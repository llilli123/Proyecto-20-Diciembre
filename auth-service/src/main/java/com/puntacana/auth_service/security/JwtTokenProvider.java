package com.puntacana.auth_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-in-minutes}")
    private long expirationInMinutes;

    private SecretKey key;

    @PostConstruct
    public void init() {
        // usando UTF-8 para construir la key
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant exp = now.plus(expirationInMinutes, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                // forma nueva, sin algoritmo explícito
                .signWith(key)
                .compact();
    }

    public Instant getExpirationInstant() {
        return Instant.now().plus(expirationInMinutes, ChronoUnit.MINUTES);
    }
}
