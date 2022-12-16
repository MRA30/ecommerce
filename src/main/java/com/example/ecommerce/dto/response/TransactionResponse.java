package com.example.ecommerce.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

  private Long id;

  private String customerName;

  private String customerEmail;

  private String customerPhoneNumber;

  private String customerAddress;

  private String totalPrice;

  private String status;

  List<OrderResponse> orders;

}
