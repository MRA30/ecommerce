package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.request.CartRequest;
import com.example.ecommerce.dto.response.CartResponse;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;


@Transactional
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;

  private UserService userService;

  private OrderService orderService;

  private ProductService productService;


  @Override
  public Cart findById(long Id) {
    return cartRepository.findById(Id);
  }

  @Override
  public void addProductToCart(CartRequest cartRequest) {
    User user = userService.findById(1);
    Cart cart = Cart.builder()
        .user(user)
        .build();
    Cart savedCart = cartRepository.save(cart);
    for (int i = 0; i < cartRequest.getProductId().size(); i++) {
      Product product = productService.findById(cartRequest.getProductId().get(i).getProductId());
      Order order = Order.builder()
          .cart(savedCart)
          .product(product)
          .quantity(cartRequest.getProductId().get(i).getQuantity())
          .build();
      orderService.createOrder(order);
    }

  }

  @Override
  public void removeProductFromCart(long productId) {
    orderService.deleteOrder(productId);
  }

  @Override
  public void updateProductInCart(CartRequest cartRequest) {

  }

  @Override
  public CartResponse findCartByUserId(long userId) {
    return null;
  }
}
