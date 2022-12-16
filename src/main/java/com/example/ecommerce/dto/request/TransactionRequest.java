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
public class TransactionRequest {

  @NotNull(message = "cartId is required")
  private Long cartId;

  @NotNull(message = "addressId is required")
  private Long addressId;

  @NotNull(message = "orderId is required")
  private Long orderId;

}
