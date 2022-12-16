package com.example.ecommerce.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateProfile {

  @NotEmpty(message = "fullName is required")
  private String fullName;

  @NotEmpty(message = "email is required")
  @Email(message = "email is not valid")
  private String email;

  @NotEmpty
  @Pattern(regexp = "(0)8[1-9][0-9]{7,11}$", message = "phone number is not valid")
  private String phoneNumber;

}
