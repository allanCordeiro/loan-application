package com.allancordeiro.creditanalysis.infrastructure.security.login;

import com.allancordeiro.creditanalysis.infrastructure.security.login.userdetails.UserDetailData;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerInputDto;
import com.allancordeiro.creditanalysis.utils.EnvProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    public final AuthenticationManager authenticationManager;

    @Autowired
    private static String getSecretKey;


    public static final Long TOKEN_EXPIRATION = Long.valueOf(new EnvProperties().getProperty("jwt.token.expiration"));
    public static final String TOKEN_GUID = new EnvProperties().getProperty("jwt.secret.key");

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginCustomerInputDto userLogin = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginCustomerInputDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLogin.email(),
                            userLogin.password(),
                            new ArrayList<>()
                    ));

        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        UserDetailData userData = (UserDetailData) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(userData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(TOKEN_GUID));

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
