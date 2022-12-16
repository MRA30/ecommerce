package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.request.ProductRequest;
import com.example.ecommerce.dto.response.ProductResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private Product findById(long id){
    return productRepository.findById(id);
  }

  @Override
  public void createProduct(ProductRequest productRequest) {
    Product product = Product.builder()
        .name(productRequest.getName())
        .stock(productRequest.getStock())
        .description(productRequest.getDescription())
        .price(productRequest.getPrice())
        .build();
    productRepository.save(product);
  }

  @Override
  public void updateProduct(long id, ProductRequest productRequest) {


  }

  @Override
  public ProductResponse findProductById(long id) {
    return null;
  }

  @Override
  public Page<ProductResponse> findAllProductWithPaging(String keyword, Pageable pageable) {
    return null;
  }
}
