package com.example.ecommerce.exception;

import com.example.ecommerce.util.DefaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationHandlers implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse,
      AuthenticationException authException) throws IOException {
    log.error("[ UNAUTHORIZED ERROR ]", authException.getMessage());
    Map<String, String> error = new HashMap<>();
    error.put("error", authException.getMessage());
    DefaultResponse<Object> response = new DefaultResponse<>("Error", true, error,
        HttpStatus.UNAUTHORIZED.value());
    String responseToString = objectMapper.writeValueAsString(response);
    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    httpServletResponse.getWriter().write(responseToString);
  }
}
