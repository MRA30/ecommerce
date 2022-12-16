package com.example.ecommerce.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

  @NotEmpty(message = "email is required")
  @Email(message = "email is invalid")
  private String email;

  @NotEmpty(message = "password is required")
  private String password;


}
