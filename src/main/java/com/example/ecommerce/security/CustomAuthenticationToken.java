package com.example.ecommerce.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;
import java.util.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomAuthenticationToken implements Authentication {

  private long userId;

  private String fullName;

  private String userName;

  private String email;

  private String role;

  private String tokenId;

  private boolean authenticated;

  private WebAuthenticationDetails webAuthenticationDetails;

  private String authority;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(authority));
  }

  @Override
  public Object getCredentials() {
    return tokenId;
  }

  @Override
  public Object getDetails() {
    return webAuthenticationDetails;
  }

  @Override
  public Object getPrincipal() {
    return email;
  }

  @Override
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return userName;
  }

  public long getUserId() {
    return userId;
  }
}
