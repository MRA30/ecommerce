package com.example.ecommerce.dto.request;

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
public class ProductRequest {

  @NotEmpty(message = "name is required")
  private String name;

  @NotNull(message = "stock is required")
  private Integer stock;

  @NotNull(message = "price is required")
  private Double price;

  @NotEmpty(message = "description is required")
  private String description;

}
