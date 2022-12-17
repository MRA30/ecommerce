package com.example.ecommerce.controller;

import com.example.ecommerce.util.DefaultResponse;
import com.example.ecommerce.dto.request.ProductRequest;
import com.example.ecommerce.dto.response.ProductResponse;
import com.example.ecommerce.service.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<DefaultResponse<?>> createProduct(
      @Valid @RequestBody ProductRequest productRequest) {
    productService.createProduct(productRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new DefaultResponse<>("Product Created", false, null, HttpStatus.CREATED.value()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DefaultResponse<ProductResponse>> findProductById(@PathVariable("id") long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Product Found", false, productService.findProductById(id),
            HttpStatus.OK.value()));
  }

  @GetMapping
  public ResponseEntity<DefaultResponse<Page<ProductResponse>>> findAllProductWithPaging(
      @RequestParam(defaultValue = "") String keyword,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDirection) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Product Found", false,
            productService.findAllProductWithPaging(keyword, page, size, sortBy, sortDirection),
            HttpStatus.OK.value()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<DefaultResponse<?>> updateProduct(@PathVariable("id") long id, @Valid @RequestBody ProductRequest productRequest) {
    productService.updateProduct(id, productRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Product Updated", false, null, HttpStatus.OK.value()));
  }

}


