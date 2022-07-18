package com.allancordeiro.creditanalysis.infrastructure.security.login;

import com.allancordeiro.creditanalysis.utils.EnvProperties;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtSecret {
    public SecretKey getSecret() {
        String secret = new EnvProperties().getProperty("JWT_SECRET");
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
