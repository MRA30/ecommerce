package com.example.ecommerce.security;

import com.example.ecommerce.Constant.Constant;
import com.example.ecommerce.service.UserService;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ApplicationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    log.info("[ FILTER PER REQUEST ]");

    Predicate<HttpServletRequest> isApiSecured = r -> Constant.PERMIT_ALL_URIS_LIST.stream()
        .noneMatch(uri -> r.getRequestURI().contains(uri));

    if (isApiSecured.test(request) && request.getCookies() != null
        && request.getCookies().length > 0) {
      String token = Arrays.stream(request.getCookies())
          .filter(c -> c.getName().equals("token")).collect(
              Collectors.toList()).get(0).getValue();

      Claims claims = jwtUtil.getClaims(token);

      log.info("[ CHECKING TOKEN EXPIRY ]");
      if (claims.getExpiration().before(new Date())) {
        log.info("Token Expired");
        throw new AuthenticationException("Token Expired") {
          private static final long serialVersionUID = 1L;
        };
      }
//      log.info("[ CHECKING TOKEN VALIDITY ]");
//      System.out.println(claims);
//      if (userService.findByEmail(claims.getSubject()) == null) {
//        throw new AuthenticationException("Token Not Valid") {
//          private static final long serialVersionUID = 1L;
//        };
//      }

      log.info(" [ CLAIMS COSTUME TOKEN ]");
      CustomAuthenticationToken customAuthenticationToken =
          CustomAuthenticationToken.builder()
              .authenticated(true)
              .tokenId(claims.getId())
              .email((String) claims.get("email"))
              .fullName((String) claims.get("fullName"))
              .userName((String) claims.get("userName"))
              .role((String) claims.get("role"))
              .authority((String) claims.get("authorities"))
              .webAuthenticationDetails(new WebAuthenticationDetailsSource().buildDetails(request))
              .build();
      SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
      Cookie cookie = new Cookie("token", token);
      cookie.setSecure(true);
      cookie.setHttpOnly(true);
      cookie.setPath("/");
      cookie.setMaxAge(24 * 60 * 60);

      log.info("[ SET COOKE TO HTTPSERVLETRESPONSE ]");
      response.addCookie(cookie);
    }
    filterChain.doFilter(request, response);
  }
}
