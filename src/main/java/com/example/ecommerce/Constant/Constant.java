package com.example.ecommerce.Constant;

import java.util.Arrays;
import java.util.List;

public class Constant {

  public static final String[] PERMIT_ALL_URIS = {"/api/users/login", "/api/users/forgot-password",
      "/api/users/reset-password/**", "/api/users/customer/register", "/swagger-ui/**",
      "/swagger-ui/", "/swagger-resources/**",
      "/swagger-ui.html",
      "/v2/api-docs",
      "/webjars/**"};
  public static final List<String> PERMIT_ALL_URIS_LIST = Arrays.asList("/users/login",
      "/api/users/forgot-password", "/api/users/reset-password/**", "/api/users/customer/register",
      "/swagger-ui/**", "/swagger-ui/", "/swagger-resources/**",
      "/swagger-ui.html",
      "/v2/api-docs",
      "/webjars/**");

  private Constant() {
  }

}
