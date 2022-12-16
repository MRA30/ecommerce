package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.OrderRequest;
import com.example.ecommerce.dto.response.OrderResponse;
import com.example.ecommerce.model.Order;

import java.util.List;

public interface OrderService {

  List<OrderResponse> findByCartId(long cartId);

  void createOrder(Order order);

  void updateOrder(long id, int quantity);

  void deleteOrder(long id);

  Order findById(long orderId);

}
