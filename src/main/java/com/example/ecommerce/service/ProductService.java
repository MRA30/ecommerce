package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.ProductRequest;
import com.example.ecommerce.dto.response.ProductResponse;
import com.example.ecommerce.model.Product;

import org.springframework.data.domain.Page;

public interface ProductService {

  Product save(Product product);

  void saveAfterSold(long id, int quantity);

  void saveAfterCancel(long id, int quantity);

  Product findById(long id);

  void createProduct(ProductRequest productRequest);

  void updateProduct(long id, ProductRequest productRequest);

  ProductResponse findProductById(long id);

  Page<ProductResponse> findAllProductWithPaging(String keyword, int page, int size, String sortBy,
      String sortDirection);

}
