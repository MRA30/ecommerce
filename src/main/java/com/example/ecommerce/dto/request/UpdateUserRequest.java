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
public class UpdateUserRequest {

  @NotEmpty(message = "fullName is required")
  private String fullName;

  @NotEmpty(message = "email is required")
  @Email(message = "email not valid")
  private String email;

  @NotEmpty(message = "phoneNumber is required")
  @Pattern(regexp = "^(0)8[1-9][0-9]{6,10}$", message = "phoneNumber not valid")
  private String phoneNumber;

  private boolean status;

}
