package com.example.ms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class MyAuthorizationFilter extends BasicAuthenticationFilter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private Environment environment;

    @Autowired
    public MyAuthorizationFilter(AuthenticationManager authenticationManager,
                                 Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String authorizationHeader = request.getHeader(environment.getProperty("token.header.name"));
        if (authorizationHeader == null || !authorizationHeader.startsWith(environment.getProperty("token.header.prefix"))) {
            chain.doFilter(request, response);
            LOG.error("Token failed");
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(authorizationHeader);
        LOG.info("Token passed");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authorizationHeader) {

        byte[] secretKey = environment.getProperty("token.jwt.secretKey").getBytes();
        String token = authorizationHeader
                .replace(environment.getProperty("token.header.prefix"), "");

        LOG.info("token = {}", token);
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey))
                    .build()
                    .parseClaimsJws(token);

            String userId = claimsJws.getBody().getSubject();

            LOG.info("userId = {}", userId);
            if (userId == null) return null;

            LOG.info("Return new UsernamePasswordAuthenticationToken");
            return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trust", token));
        }
    }
}