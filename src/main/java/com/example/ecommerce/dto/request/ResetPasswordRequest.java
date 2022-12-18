package com.example.ecommerce.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {

  @NotEmpty(message = "password")
  @Size(min = 8, max = 20, message = "password must be between 8 and 20 characters")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$", message = "password must contain at least one digit, one lowercase, one uppercase, one special character and no whitespace")
  private String password;

}
