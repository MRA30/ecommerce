package com.example.ecommerce.dto.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;
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

  @NotNull(message = "addressId is required")
  private long addressId;

  @NotEmpty(message = "order is required")
  private List<OrderRequest> orderRequests;

}
