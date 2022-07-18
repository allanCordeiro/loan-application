package com.allancordeiro.creditanalysis.infrastructure.security.login;

import com.allancordeiro.creditanalysis.utils.EnvProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Authorizer {
    public String authorize(String customerEmail) {
        return Jwts.builder()
                .setSubject(customerEmail)
                .setIssuer("localhost:8080") //TODO::change it
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(
                                LocalDateTime.now().plusMinutes(15L)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()
                        ))
                .signWith(this.jwtSecret(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey jwtSecret() {
        String secret = new EnvProperties().getProperty("JWT_SECRET");
        System.out.println("meu JWT é " + secret);
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
