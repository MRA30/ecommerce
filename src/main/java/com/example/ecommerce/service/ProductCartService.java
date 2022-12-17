package com.example.ecommerce.service;

import com.example.ecommerce.model.ProductCart;

import java.util.List;

public interface ProductCartService {

  void save(ProductCart productCart);

  ProductCart findProductCartById(long id);

  void delete(ProductCart productCart);

  List<ProductCart> findProductCartByCartId(long cartId);

}
