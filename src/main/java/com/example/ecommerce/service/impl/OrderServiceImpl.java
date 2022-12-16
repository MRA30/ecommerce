package com.example.ecommerce.service.impl;

import com.example.ecommerce.Util.CurrencyConvert;
import com.example.ecommerce.dto.request.OrderRequest;
import com.example.ecommerce.dto.response.OrderResponse;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.OrderService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private OrderResponse mappeOrderToOrderResponse(Order order) {
    return OrderResponse.builder()
        .id(order.getId())
        .productName(order.getProduct().getName())
        .productPrice(CurrencyConvert.convertToRupiah(order.getProduct().getPrice()))
        .productQuantity(order.getQuantity())
        .productStock(order.getProduct().getStock())
        .build();
  }



  @Override
  public List<OrderResponse> findByCartId(long cartId) {
    List<Order> orders = orderRepository.findByCartId(cartId);
    return orders.stream().map(this::mappeOrderToOrderResponse).collect(Collectors.toList());
  }

  @Override
  public void createOrder(Order order) {
    orderRepository.save(order);

  }

  @Override
  public void updateOrder(long id, int quantity) {
    Order order = findById(id);
    order.setQuantity(quantity);
    orderRepository.save(order);

  }

  @Override
  public void deleteOrder(long id) {
    orderRepository.deleteById(id);
  }

  @Override
  public Order findById(long id) {
    return orderRepository.findById(id);
  }
}
