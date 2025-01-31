package com.api_penta_dev.infra.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private static final String SECRET_KEY = "mySecretKey";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2;

    public String generateToken(String userId, String name, boolean isGuest) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);
        claims.put("guest", isGuest);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
