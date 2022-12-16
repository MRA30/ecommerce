package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.CartRequest;
import com.example.ecommerce.dto.response.CartResponse;
import com.example.ecommerce.model.Cart;

public interface CartService {

  Cart findById(long Id);

  void addProductToCart(CartRequest cartRequest);

  void removeProductFromCart(long productId);

  void updateProductInCart(CartRequest cartRequest);

  CartResponse findCartByUserId(long userId);
}
