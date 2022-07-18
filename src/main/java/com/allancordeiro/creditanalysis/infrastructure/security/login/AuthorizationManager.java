package com.allancordeiro.creditanalysis.infrastructure.security.login;


import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.crypto.SecretKey;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


@Provider
@Authorize
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationManager implements ContainerRequestFilter {
    private final SecretKey secretKey = new JwtSecret().getSecret();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        try {
            String token = authorizationHeader.substring("Bearer".length()).trim();
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parsePlaintextJws(token);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
