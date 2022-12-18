package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.ProductCart;
import com.example.ecommerce.repository.ProductCartRepository;
import com.example.ecommerce.service.ProductCartService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCartServiceImpl implements ProductCartService {

  private final ProductCartRepository productCartRepository;

  @Override
  public void save(ProductCart productCart){
    productCartRepository.save(productCart);
  }

  @Override
  public ProductCart findProductCartByCartIdAndProductId(long cartId, long productId) {
    return productCartRepository.findByCartIdAndProductId(cartId, productId);
  }

  @Override
  public ProductCart findProductCartById(long id) {
    return productCartRepository.findById(id);
  }

  @Override
  public void delete(ProductCart productCart) {
    productCartRepository.delete(productCart);
  }

  @Override
  public List<ProductCart> findProductCartByCartId(long cartId) {
    return productCartRepository.findByCartId(cartId);
  }


}
