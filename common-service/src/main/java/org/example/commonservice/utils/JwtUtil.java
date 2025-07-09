package org.example.commonservice.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final String SECRET = "8b77d7fc7dc4fecb1cbe419e2b0512625e8cf3599fd8b141ec077c7a2e4d0459"; // 64-byte Base64 string
    private static final long EXPIRATION = 1;
    private final static byte[] keyBytes = java.util.Base64.getDecoder().decode(SECRET);
    private final static SecretKey key = Keys.hmacShaKeyFor(keyBytes);

    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .issuer("me")
                .subject("Bob")
                .audience().add("you").and()
                .claims(claims)
                .issuedAt(new Date())
                .notBefore(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .id(UUID.randomUUID().toString())
                .signWith(key)
                .compact();
    }

    public static Jws<Claims> parseToken(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return null;
    }

    public static boolean isTokenExpired(String token) {
        if (Objects.requireNonNull(parseToken(token) != null)) {
            return parseToken(token).getPayload().getExpiration().after(new Date());
        }
        return false;
    }

    public static Claims validateToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseClaimsJws(token).getBody();
    }
}