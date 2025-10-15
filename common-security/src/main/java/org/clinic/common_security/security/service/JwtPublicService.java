package org.clinic.common_security.security.service;

import io.jsonwebtoken.Jwts;
import org.clinic.common_security.security.enums.Permission;
import org.clinic.common_security.security.enums.Role;
import org.clinic.common_security.security.jwt.JwtPublicProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtPublicService {
    private static final Logger logger = LoggerFactory.getLogger(JwtPrivateService.class);
    private final PublicKey publicKey;

    public JwtPublicService(JwtPublicProperties jwtPublicProperties) {
        this.publicKey = loadPublicKey(jwtPublicProperties.getPublicKeyPath());
    }

    private PublicKey loadPublicKey(String path) {
        if (path == null) return null;
        try (InputStream inputStream = new ClassPathResource(path).getInputStream()) {
            byte[] keyBytes = inputStream.readAllBytes();
            String publicKeyPEM = new String(keyBytes)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Cannot load public key", e);
        }
    }

    public Map<String, Object> parseClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public Set<Role> getRoles(Map<String, Object> claims) {
        List<String> roles = (List<String>) claims.get("role");

        if (roles == null) {
            return Set.of();
        }

        return roles.stream()
                .map(String::toUpperCase)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    public Set<Permission> getPermissions(Map<String, Object> claims) {
        List<String> permissions = (List<String>) claims.get("permission");

        if (permissions == null) {
            return Set.of();
        }

        return permissions.stream()
                .map(String::toUpperCase)
                .map(Permission::valueOf)
                .collect(Collectors.toSet());
    }
}
