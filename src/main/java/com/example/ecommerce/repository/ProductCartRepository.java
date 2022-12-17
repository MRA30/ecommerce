package com.example.ecommerce.repository;

import com.example.ecommerce.model.ProductCart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {

  ProductCart findById(long id);

  List<ProductCart> findByCartId(long id);

}
