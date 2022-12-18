package com.example.ecommerce.repository;

import com.example.ecommerce.model.ProductCart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {

  ProductCart findById(long id);

  ProductCart findByCartIdAndProductId(long cartId, long productId);

  List<ProductCart> findByCartId(long id);

}
