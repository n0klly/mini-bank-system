package ru.noklly.userservice.security.token;

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
    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }
    private Claims getClaims(String jwtToken){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
    public boolean checkEmail(String email, String jwtToken){
        try {
        return email.equals(getClaims(jwtToken).getSubject());
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
}
