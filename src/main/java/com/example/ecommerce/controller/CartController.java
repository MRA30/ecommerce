package com.example.ecommerce.controller;

import com.example.ecommerce.Util.DefaultResponse;
import com.example.ecommerce.dto.request.CartRequest;
import com.example.ecommerce.dto.request.ProductCartRequest;
import com.example.ecommerce.dto.request.ProductCartUpdateRequest;
import com.example.ecommerce.dto.response.CartResponse;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  private final UserService userService;


  @PostMapping
  public ResponseEntity<DefaultResponse<?>> addProductToCart(
      @Valid @RequestBody CartRequest cartRequest) {
    // TODO : CHECK USER LOGIN
    long userId = 1;
    cartService.addProductToCart(userId, cartRequest);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.CREATED.value()));
  }

  @GetMapping
  public ResponseEntity<DefaultResponse<CartResponse>> getCartById() {
    // TODO : CHECK USER LOGIN
    long userId = 1;
    CartResponse cartResponse = cartService.findCartByUserId(userId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, cartResponse, HttpStatus.OK.value()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<DefaultResponse<?>> updateProductCart(@PathVariable("id") long id,
      @Valid @RequestBody ProductCartUpdateRequest productCartUpdateRequest) {
    // TODO : CHECK USER LOGIN
    long userId = 1;
    cartService.updateProductInCart(userId, id, productCartUpdateRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.OK.value()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<DefaultResponse<?>> deleteProductCart(@PathVariable("id") long id) {
    // TODO : CHECK USER LOGIN
    long userId = 1;
    cartService.removeProductFromCart(userId, id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.OK.value()));
  }


}
