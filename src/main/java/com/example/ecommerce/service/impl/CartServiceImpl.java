package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.request.CartRequest;
import com.example.ecommerce.dto.request.ProductCartUpdateRequest;
import com.example.ecommerce.dto.response.CartResponse;
import com.example.ecommerce.dto.response.ProductCartResponse;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductCart;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductCartService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;


@Transactional
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final ProductCartService productCartService;
  private final UserService userService;
  private final ProductService productService;

  @Override
  public Cart findById(long id) {
    return cartRepository.findById(id);
  }

  @Override
  public void addProductToCart(long userId, CartRequest cartRequest) {
    Cart cart = cartRepository.findByUserId(userId);
    if (cart == null) {
      System.out.println(userId);
      User user = userService.findById(userId);
      Cart newCart = Cart.builder()
          .user(user)
          .build();
      Cart savedCart = cartRepository.save(newCart);
      for (int i = 0; i < cartRequest.getProducts().size(); i++) {
        Product product = productService.findById(cartRequest.getProducts().get(i).getProductId());
        ProductCart productCart = ProductCart.builder()
            .cartId(savedCart.getId())
            .product(product)
            .quantity(cartRequest.getProducts().get(i).getQuantity())
            .build();
        productCartService.save(productCart);
      }
    } else {
      for (int i = 0; i < cartRequest.getProducts().size(); i++) {
        ProductCart productCart = productCartService.findProductCartByCartIdAndProductId(cart.getId(), cartRequest.getProducts().get(i).getProductId());
        if(productCart != null){
          productCart.setQuantity(productCart.getQuantity() + cartRequest.getProducts().get(i).getQuantity());
          productCartService.save(productCart);
        }else {
          Product product = productService.findById(
              cartRequest.getProducts().get(i).getProductId());
          ProductCart productCartNew = ProductCart.builder()
              .cartId(cart.getId())
              .product(product)
              .quantity(cartRequest.getProducts().get(i).getQuantity())
              .build();
          productCartService.save(productCartNew);
        }
      }
    }
  }

  @Override
  public void updateProductInCart(long userId, long id,
      ProductCartUpdateRequest productCartUpdateRequest) {

    ProductCart productCart = productCartService.findProductCartById(id);
    if (productCart != null) {
      Cart cart = findById(productCart.getCartId());
      if (cart.getUser().getId() == userId) {
        productCart.setQuantity(productCartUpdateRequest.getQuantity());
        productCartService.save(productCart);
      } else {
        throw new BusinessException("Product Cart Id", "Product cart not found",
            HttpStatus.BAD_REQUEST);
      }
    } else {
      throw new BusinessException("Product Cart Id", "Product cart not found",
          HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public void removeProductFromCart(long userId, long productCartId) {
    ProductCart productCart = productCartService.findProductCartById(productCartId);
    if (productCart != null) {
      Cart cart = findById(productCart.getCartId());
      if (cart.getUser().getId() == userId) {
        productCartService.delete(productCart);
      } else {
        throw new BusinessException("Product Cart Id", "Product cart not found",
            HttpStatus.BAD_REQUEST);
      }
    } else {
      throw new BusinessException("Product Cart Id", "Product cart not found",
          HttpStatus.BAD_REQUEST);
    }
  }


  @Override
  public CartResponse findCartByUserId(long userId) {
    Cart cart = cartRepository.findByUserId(userId);
    if (cart != null) {
      List<ProductCart> productCarts = productCartService.findProductCartByCartId(cart.getId());
      List<ProductCartResponse> productCartResponses = new ArrayList<>();
      for (ProductCart productCart : productCarts) {
        ProductCartResponse productCartResponse = ProductCartResponse.builder()
            .id(productCart.getId())
            .productId(productCart.getProduct().getId())
            .productName(productCart.getProduct().getName())
            .quantity(productCart.getQuantity())
            .price(productCart.getProduct().getPrice())
            .build();
        productCartResponses.add(productCartResponse);
      }
      return CartResponse.builder()
          .id(cart.getId())
          .name(cart.getUser().getFullName())
          .productCartResponses(productCartResponses)
          .build();
    } else {
      return null;
    }

  }
}
