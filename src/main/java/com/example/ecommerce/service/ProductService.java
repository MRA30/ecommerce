package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.ProductRequest;
import com.example.ecommerce.dto.response.ProductResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {


  void createProduct(ProductRequest productRequest);

  void updateProduct(long id, ProductRequest productRequest);

  ProductResponse findProductById(long id);

  Page<ProductResponse> findAllProductWithPaging(String keyword, Pageable pageable);

}
