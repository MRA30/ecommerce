package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.CartRequest;
import com.example.ecommerce.dto.request.ProductCartRequest;
import com.example.ecommerce.dto.response.CartResponse;
import com.example.ecommerce.model.Cart;

public interface CartService {

  Cart findById(long id);

  void addProductToCart(long userId, CartRequest cartRequest);

  void updateProductInCart(long userId, long id, ProductCartRequest productCartRequest);

  void removeProductFromCart(long userId, long productId);

  CartResponse findCartByUserId(long userId);
}
