package com.example.ecommerce.service.impl;

import com.example.ecommerce.Util.Pageableutil;
import com.example.ecommerce.dto.request.TransactionRequest;
import com.example.ecommerce.dto.request.TransactionUpdateRequest;
import com.example.ecommerce.dto.response.OrderResponse;
import com.example.ecommerce.dto.response.TransactionResponse;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Transaction;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.TransactionRepository;
import com.example.ecommerce.service.AddressService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.TransactionService;
import com.example.ecommerce.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;

  private final OrderService orderService;

  private final UserService userService;

  private final AddressService addressService;

  private final ProductService productService;

  private TransactionResponse convertTransactionToTransactionResponse(Transaction transaction) {
    List<OrderResponse> orderResponses = new ArrayList<>();
    for (Order order : transaction.getOrderList()) {
      OrderResponse orderResponse = orderService.convertOrderToOrderResponse(order);
      orderResponses.add(orderResponse);

    }
    return TransactionResponse.builder()
        .id(transaction.getId())
        .customerName(transaction.getUser().getFullName())
        .customerAddress(transaction.getAddress())
        .customerEmail(transaction.getUser().getEmail())
        .customerPhoneNumber(transaction.getUser().getPhoneNumber())
        .orders(orderResponses)
        .build();
  }

  private Page<TransactionResponse> findAllTransactionByCustomerPaging(long userId, int page,
      int size,
      String sortBy, String sortDir) {
    Pageable pageable = Pageableutil.createPageable(page, size, sortBy, sortDir);
    Page<Transaction> transactions = transactionRepository.findAllTransactionByCustomer(userId,
        pageable);
    List<Transaction> transactionsList = transactions.getContent();
    return new PageImpl<>(
        (transactionsList.stream().map(this::convertTransactionToTransactionResponse)
            .collect(Collectors.toList())), pageable, transactions.getTotalElements());
  }

  private Page<TransactionResponse> findAllTransactionByCustomerAndStatusPaging(long userId,
      String status, int page, int size, String sortBy, String sortDir) {
    Pageable pageable = Pageableutil.createPageable(page, size, sortBy, sortDir);
    int statusInt = Integer.parseInt(status);
    Page<Transaction> transactions = transactionRepository.findTransactionByCustomerAndStatus(
        userId, statusInt, pageable);
    List<Transaction> transactionsList = transactions.getContent();
    return new PageImpl<>(
        (transactionsList.stream().map(this::convertTransactionToTransactionResponse)
            .collect(Collectors.toList())), pageable, transactions.getTotalElements());
  }

  private Page<TransactionResponse> findAllTransactionByAdminPaging(int page, int size,
      String sortBy, String sortDir) {
    Pageable pageable = Pageableutil.createPageable(page, size, sortBy, sortDir);
    Page<Transaction> transactions = transactionRepository.findAllTransactionByAdmin(pageable);
    List<Transaction> transactionList = transactions.getContent();
    return new PageImpl<>(
        transactionList.stream().map(this::convertTransactionToTransactionResponse).collect(
            Collectors.toList()), pageable, transactions.getTotalElements());
  }

  private Page<TransactionResponse> findAllTransactionByAdminAndStatusPaging(String status,
      int page, int size, String sortBy, String sortDir) {
    Pageable pageable = Pageableutil.createPageable(page, size, sortBy, sortDir);
    int statusInt = Integer.parseInt(status);
    Page<Transaction> transactions = transactionRepository.findAllTransactionByAdminAndStatus(
        statusInt, pageable);
    List<Transaction> transactionList = transactions.getContent();
    return new PageImpl<>(
        transactionList.stream().map(this::convertTransactionToTransactionResponse).collect(
            Collectors.toList()), pageable, transactions.getTotalElements());
  }

  @Override
  public Transaction save(Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  @Override
  public Transaction findById(long id) {
    Optional<Transaction> transaction = transactionRepository.findById(id);
    if (transaction.isPresent()) {
      return transaction.get();
    } else {
      return null;
    }
  }

  @Override
  public void createTransaction(long userId, TransactionRequest transactionRequest) {
    User user = userService.findById(userId);
    Address address = addressService.findById(transactionRequest.getAddressId());
    Transaction transaction = Transaction.builder()
        .user(user)
        .address(address.getAddress())
        .build();
    Transaction transactionSave = save(transaction);

    List<Order> orderList = new ArrayList<>();
    for (int i = 0; i < transactionRequest.getOrderRequests().size(); i++) {
      Product product = productService.findById(
          transactionRequest.getOrderRequests().get(i).getProductId());
      if (product.getStock() < transactionRequest.getOrderRequests().get(i).getQuantity()) {
        String message = String.format("Sorry, product %s is out of stock", product.getName());
        throw new BusinessException("Product", message, HttpStatus.BAD_REQUEST);
      }
      Order order = Order.builder()
          .transactionId(transactionSave.getId())
          .product(product)
          .quantity(transactionRequest.getOrderRequests().get(i).getQuantity())
          .build();
      orderList.add(order);
    }
    orderService.saveAll(orderList);
    for (Order order : orderList) {
      productService.saveAfterSold(order.getProduct().getId(), order.getQuantity());
    }
  }

  @Override
  public void updateTransaction(long id, TransactionUpdateRequest transactionUpdateRequest) {
    Transaction transaction = findById(id);
    if (transaction != null) {
      transaction.setStatus(transactionUpdateRequest.getStatus());
      save(transaction);
      if (transactionUpdateRequest.getStatus() == 2) {
        for (Order order : transaction.getOrderList()) {
          Product product = productService.findById(order.getProduct().getId());
          productService.saveAfterCancel(product.getId(), order.getQuantity());
        }
      }
    } else {
      throw new BusinessException("id", "Transaction not found", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public TransactionResponse findByIdByCustomer(long userId, long id) {
    Transaction transaction = findById(id);
    TransactionResponse transactionResponse;
    if (transaction != null) {
      if (transaction.getUser().getId() != userId) {
        throw new BusinessException("id", "Transaction not found", HttpStatus.BAD_REQUEST);
      }
      transactionResponse = convertTransactionToTransactionResponse(transaction);
    } else {
      throw new BusinessException("id", "Transaction not found", HttpStatus.BAD_REQUEST);
    }
    return transactionResponse;
  }

  @Override
  public TransactionResponse findByIdAdmin(long id) {
    Transaction transaction = findById(id);
    TransactionResponse transactionResponse;
    if (transaction != null) {
      transactionResponse = convertTransactionToTransactionResponse(transaction);
    } else {
      throw new BusinessException("id", "Transaction not found", HttpStatus.BAD_REQUEST);
    }
    return transactionResponse;
  }

  @Override
  public Page<TransactionResponse> findAllTransactionByCustomer(long userId, String status,
      int page, int size, String sortBy, String sortDir) {
    Page<TransactionResponse> transactions;
    if (status.equals("all")) {
      transactions = findAllTransactionByCustomer(userId, status, page, size, sortBy, sortDir);
    } else {
      transactions = findAllTransactionByCustomerAndStatusPaging(userId, status, page, size, sortBy,
          sortDir);
    }
    return transactions;
  }

  @Override
  public Page<TransactionResponse> findAllTransactionByAdmin(String status, int page, int size,
      String sortBy, String sortDir) {
    Page<TransactionResponse> transactions;
    if (status.equals("all")) {
      transactions = findAllTransactionByAdminPaging(page, size, sortBy, sortDir);
    } else {
      transactions = findAllTransactionByAdminAndStatusPaging(status, page, size, sortBy,
          sortDir);
    }
    return transactions;
  }
}
