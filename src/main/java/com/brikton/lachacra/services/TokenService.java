package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
public class TokenService {
    @Value("${auth.secret.key}")
    private String secreKey;

    @Value("${auth.token.expiration.msec}")
    private Long expirationToken;

    public Token generateAccessToken(String subject) {
        var key = Keys.hmacShaKeyFor(secreKey.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        long duration = now.getTime() + expirationToken;
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .claim("username", subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
        return new Token(Token.TokenType.ACCESS, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    public String getUsernameFromToken(String token) {
        try {
            var key = Keys.hmacShaKeyFor(secreKey.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();
            return claims.get("username").toString();
        } catch (Exception e) {
            log.error("Ocurrió un error", e);
            return "";
        }
    }

    public boolean validateToken(String token) {
        if (token == null)
            return false;

        try {
            var key = Keys.hmacShaKeyFor(secreKey.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parse(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
