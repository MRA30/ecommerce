package com.example.ecommerce.config;


import com.example.ecommerce.Constant.Constant;
import com.example.ecommerce.exception.AccessDeniedHandlers;
import com.example.ecommerce.exception.AuthenticationHandlers;
import com.example.ecommerce.security.ApplicationFilter;
import com.example.ecommerce.security.JwtUtil;
import com.example.ecommerce.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  private final AccessDeniedHandlers accessDeniedHandlers;

  private final AuthenticationHandlers authenticationHandlers;

  private final JwtUtil jwtUtil;

  public ApplicationFilter authenticationFilter() {
    return new ApplicationFilter(jwtUtil);
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(Constant.PERMIT_ALL_URIS)
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

  @Bean
  public InternalResourceViewResolver defaultViewResolver() {
    return new InternalResourceViewResolver();
  }

}
