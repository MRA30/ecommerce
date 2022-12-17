package com.example.ecommerce.service;

import com.example.ecommerce.dto.response.OrderResponse;
import com.example.ecommerce.model.Order;

import java.util.List;

public interface OrderService {

  void save(Order order);

  void saveAll(List<Order> orders);

  List<Order> findByTransactionId(long cartId);

  void createOrder(Order order);

  Order findById(long orderId);

  OrderResponse convertOrderToOrderResponse(Order order);

}
