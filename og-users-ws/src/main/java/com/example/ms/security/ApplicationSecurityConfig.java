package com.example.ms.security;

import com.example.ms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final Environment env;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(Environment env,
                                     UserService userService,
                                     PasswordEncoder passwordEncoder) {
        this.env = env;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        LOG.info("Configure userService and passwordEncoder");
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOG.info("Configure Http");

        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(getAuthenticationFilter())
                    .authorizeRequests()
                    .antMatchers("/**")
                    .hasIpAddress(env.getProperty("gateway.ip"));
    }

    private MyAuthenticationFilter getAuthenticationFilter() throws Exception {
        LOG.info("Create a new my MyAuthFilter object");
        MyAuthenticationFilter myAuthenticationFilter =
                new MyAuthenticationFilter(env, userService, authenticationManager());
        myAuthenticationFilter.setFilterProcessesUrl("/users-api/login");
        return myAuthenticationFilter;
    }
}