package com.edh.pod.generator.api.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtBuilder {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String username){
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + (100000 * 30));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public boolean isTokenValid(String token){
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch (JwtException jwtException){
            return false;
        }
    }

    public Map<?, ?> decodeToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        try{
            return (Map<?, ?>) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        }catch(JwtException jwtException){
            return null;
        }
    }
}
