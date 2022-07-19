package com.allancordeiro.creditanalysis.infrastructure.security.login;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ValidateFilter extends BasicAuthenticationFilter {
    public static final String HEADER_KEY = "Authorization";
    public static final String HEADER_PREFIX_VALUE = "Bearer ";

    public ValidateFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String key = request.getHeader(HEADER_KEY);
        if (key == null) {
            chain.doFilter(request, response);
            return;
        }

        if(!key.startsWith(HEADER_PREFIX_VALUE)) {
            chain.doFilter(request, response);
            return;
        }

        String token = key.replace(HEADER_PREFIX_VALUE, "");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String user = JWT.require(Algorithm.HMAC512(AuthenticationFilter.TOKEN_GUID))
                .build()
                .verify(token)
                .getSubject();

        if(user == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
}
