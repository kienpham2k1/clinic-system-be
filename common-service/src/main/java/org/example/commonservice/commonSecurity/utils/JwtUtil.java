package org.example.commonservice.commonSecurity.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.example.commonservice.commonSecurity.config.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final JwtProperties props;
    private final SecretKey key;

    public JwtUtil(JwtProperties props) {
        this.props = props;
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(props.getSecret()));
    }

    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .notBefore(new Date())
                .expiration(new Date(System.currentTimeMillis() + props.getExpiration()))
                .id(UUID.randomUUID().toString())
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
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

    public boolean isTokenExpired(String token) {
        if (Objects.requireNonNull(parseToken(token) != null)) {
            return parseToken(token).getPayload().getExpiration().after(new Date());
        }
        return false;
    }

    public Claims validateToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseClaimsJws(token).getBody();
    }
}