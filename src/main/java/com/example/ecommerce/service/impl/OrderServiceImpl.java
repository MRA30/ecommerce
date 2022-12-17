package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.response.OrderResponse;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.OrderService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  public void save(Order order) {
    orderRepository.save(order);
  }

  @Override
  public void saveAll(List<Order> orders) {
    for (Order order : orders) {
      save(order);
    }
  }

  @Override
  public List<Order> findByTransactionId(long transactionId) {
    return orderRepository.findByTransactionId(transactionId);
  }

  @Override
  public void createOrder(Order order) {
    save(order);
  }

  @Override
  public Order findById(long id) {
    return orderRepository.findById(id);
  }

  @Override
  public OrderResponse convertOrderToOrderResponse(Order order) {
    return OrderResponse.builder()
        .id(order.getId())
        .productName(order.getProduct().getName())
        .productQuantity(order.getQuantity())
        .productPrice(order.getProduct().getPrice())
        .build();
  }
}
