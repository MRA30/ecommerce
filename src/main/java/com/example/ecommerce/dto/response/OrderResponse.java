package com.example.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderResponse {

  private Long id;

  private String productName;

  private String productPrice;

  private String productQuantity;

  private String productStock;

}
