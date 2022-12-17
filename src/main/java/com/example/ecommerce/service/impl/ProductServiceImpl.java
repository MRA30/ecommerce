package com.example.ecommerce.service.impl;

import com.example.ecommerce.Util.CurrencyConvert;
import com.example.ecommerce.dto.request.ProductRequest;
import com.example.ecommerce.dto.response.ProductResponse;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private ProductResponse mappeProductToProductResponse(Product product){
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .price(CurrencyConvert.convertToRupiah(product.getPrice()))
        .description(product.getDescription())
        .build();
  }

  @Override
  public Product save(Product product) {
    return productRepository.save(product);
  }

  @Override
  public void saveAfterSold(long id, int quantity) {
    Product product = findById(id);
    int stock = product.getStock();
    int sold = product.getSold();
    product.setStock(stock - quantity);
    product.setSold(sold + quantity);
    save(product);
  }

  @Override
  public void saveAfterCancel(long id, int quantity) {
    Product product = findById(id);
    int stock = product.getStock();
    int sold = product.getSold();
    product.setStock(stock + quantity);
    product.setSold(sold - quantity);
    save(product);
  }

  public Product findById(long id){
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
    save(product);
  }

  @Override
  public void updateProduct(long id, ProductRequest productRequest) {
    Product product = findById(id);
    if(product != null){
      product.setName(productRequest.getName());
      product.setStock(productRequest.getStock());
      product.setDescription(productRequest.getDescription());
      product.setPrice(productRequest.getPrice());
      productRepository.save(product);
    }else {
      throw new BusinessException("id",  "Product not found", HttpStatus.NOT_FOUND);
    }

  }

  @Override
  public ProductResponse findProductById(long id) {
    Product product = findById(id);
    if(product != null){
      return mappeProductToProductResponse(product);
    }else {
      throw new BusinessException("id",  "Product not found", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public Page<ProductResponse> findAllProductWithPaging(String keyword, int page, int size, String sortBy, String sortDirection) {

    Pageable pageable;
    if(sortDirection.equals("asc")){
      pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
    }else {
      pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
    }
    return productRepository.findByName(keyword, pageable).map(this::mappeProductToProductResponse);
  }
}
