package com.example.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

  private Long id;

  private String fullName;

  private String email;

  private String phoneNumber;

  private long roleId;

  private String rolename;

  private String isVerified;

  private String status;

}
