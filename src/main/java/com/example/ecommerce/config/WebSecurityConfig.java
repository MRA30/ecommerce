package com.example.ecommerce.config;


import com.example.ecommerce.exception.AccessDeniedHandlers;
import com.example.ecommerce.exception.AuthenticationHandlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  private static final String[] PERMIT_ALL_URIS = {"/users/login", "/users/forgot-password",
      "/users/reset-password", "/swagger-ui/",};

  private final AccessDeniedHandlers accessDeniedHandlers;

  private final AuthenticationHandlers authenticationHandlers;

  public ApplicationFilter authenticationFilter() {
    return new ApplicationFilter(jwtUtils);
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(PERMIT_ALL_URIS)
        .permitAll()
        .anyRequest()
        .authenticated();

    http
        .headers()
        .frameOptions()
        .disable()
        .and()
        .logout()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationHandlers)
        .accessDeniedHandler(accessDeniedHandlers)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }


}
