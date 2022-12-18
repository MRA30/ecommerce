package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.ForgotPasswordRequest;
import com.example.ecommerce.dto.request.LoginRequest;
import com.example.ecommerce.dto.request.ResetPasswordRequest;
import com.example.ecommerce.dto.response.UserResponse;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.util.DefaultResponse;
import com.example.ecommerce.util.TokenUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<DefaultResponse<?>> login(@Valid @RequestBody LoginRequest loginRequest) {
    String token = userService.login(loginRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.SET_COOKIE, TokenUtil.composeCookie(token, 60 * 60 * 24))
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.OK.value()));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<DefaultResponse<?>> forgotPassword(@Valid @RequestBody
  ForgotPasswordRequest forgotPasswordRequest) {
    String token = userService.forgotPassword(forgotPasswordRequest.getEmail());
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, token, HttpStatus.OK.value()));
  }

  @PostMapping("/reset-password/{token}")
  public ResponseEntity<DefaultResponse<?>> resetPassword(@PathVariable("token") String token,
      @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
    userService.resetPassword(token, resetPasswordRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.OK.value()));
  }

  @GetMapping("/profile")
  public ResponseEntity<DefaultResponse<UserResponse>> getProfile() {
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    long userId = userService.user().getId();
    UserResponse userResponse = userService.profile(userId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, userResponse, HttpStatus.OK.value()));
  }

}
