package ru.noklly.accountservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims getClaims(String jwtToken){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
    public boolean checkId(Long id, String jwtToken){
        try {
        return getClaims(jwtToken).get("userId",Long.class)==id;
    }
       catch(RuntimeException runtimeException){
        return false;
        }
    }
    public boolean isTokenExpired(String jwtToken){
       try{ Claims claims = getClaims(jwtToken);
        return claims.getExpiration().before(new Date(System.currentTimeMillis()));
       }
       catch(ExpiredJwtException expiredJwtException){
           return true;
       } catch (JwtException | IllegalArgumentException e) {
           return true;
       }
    }
    public String extractEmail(String jwtToken) {
        return getClaims(jwtToken).getSubject();
    }
    public Long extractUserId(String jwtToken) {
        return getClaims(jwtToken).get("userId",Long.class);
    }
}
