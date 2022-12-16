package com.example.ecommerce.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {

  @NotNull(message = "customerId is required")
  private Long customerId;

  @NotNull(message = "productId is required")
  private Long productId;

}
