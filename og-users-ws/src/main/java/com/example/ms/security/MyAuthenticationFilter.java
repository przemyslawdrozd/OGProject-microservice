package com.example.ms.security;

import com.example.ms.model.LoginRequestModel;
import com.example.ms.model.UserResponse;
import com.example.ms.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final Environment env;
    private final UserService userService;

    @Autowired
    public MyAuthenticationFilter(Environment env, UserService userService,
                                  AuthenticationManager authenticationManager) {
        this.userService = userService;
        super.setAuthenticationManager(authenticationManager);
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            LoginRequestModel creds = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestModel.class);

            LOG.trace("Attempt Request for: {}", creds.getEmail());

            return this.getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword()));

        } catch (IOException e) {
            LOG.error("Attempt authentication failed");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        String userEmail = ((User) authResult.getPrincipal()).getUsername();
        LOG.info("Successful auth called: {}", userEmail);

        UserResponse userResponse = userService.getUserDetailsByEmail(userEmail);
        byte[] key = env.getProperty("token.jwt.secretKey").getBytes();

        String token = Jwts.builder()
                .setSubject(userResponse.getUserId())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(Integer.parseInt(env.getProperty("token.expirationDays")))))
                .signWith(Keys.hmacShaKeyFor(key))
                .compact();

        LOG.info("Token generated: {}", token);
        response.addHeader("token", token);
        response.addHeader("userId", userResponse.getUserId());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        LOG.error("Authentication Failure: Invalid email of password");
        super.unsuccessfulAuthentication(request, response, failed);
    }
}