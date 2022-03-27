package com.brikton.lachacra.services;

import com.brikton.lachacra.entities.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
public class TokenService {

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${auth.tokenExpirationMsec}")
    private Long expirationToken;

    //TODO: refactor to use DateUtil
    public Token generateAccessToken(String subject) {
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
            Jwts.parserBuilder().setSigningKey(key).build().parse(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
