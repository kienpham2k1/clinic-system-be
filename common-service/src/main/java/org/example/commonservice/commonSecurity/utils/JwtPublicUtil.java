package org.example.commonservice.commonSecurity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.commonservice.commonSecurity.config.JwtPublicProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtPublicUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtPrivateUtil.class);
    private final PublicKey publicKey;

    public JwtPublicUtil(JwtPublicProperties jwtPublicProperties) {
        this.publicKey = loadPublicKey(jwtPublicProperties.getPublicKeyPath());
    }

    public Claims validateToken(String token) {
        return Jwts
                .parser()
                .verifyWith(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
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
}
