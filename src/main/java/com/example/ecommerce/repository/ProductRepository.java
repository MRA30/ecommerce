package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Product findById(long id);

  @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
  Page<Product> findByName(String keyword, Pageable pageable);

}
