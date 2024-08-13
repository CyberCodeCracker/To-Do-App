package com.amouri.To_Do.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final String SECRET_KEY = "";

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
    }
}
