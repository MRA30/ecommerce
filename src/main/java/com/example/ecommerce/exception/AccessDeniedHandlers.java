package com.example.ecommerce.exception;

import com.example.ecommerce.util.DefaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class AccessDeniedHandlers implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    log.error("[ FORBIDDEN ACCESS ERROR ]", accessDeniedException.getMessage());
    Map<String, String> error = new HashMap<>();
    error.put("error", accessDeniedException.getMessage());
    DefaultResponse<Object> res = new DefaultResponse<>("Error", true, error,
        HttpStatus.FORBIDDEN.value());
    String responseToString = objectMapper.writeValueAsString(res);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(responseToString);
  }
}
