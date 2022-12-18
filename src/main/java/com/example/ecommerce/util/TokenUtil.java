package com.example.ecommerce.util;

import org.springframework.http.ResponseCookie;

public class TokenUtil {

  public static String composeCookie(String token, int maxAge) {
    return ResponseCookie.from("token", token)
        .secure(true)
        .httpOnly(true)
        .sameSite("None")
        .path("/")
        .maxAge(maxAge)
        .build().toString();
  }

}
