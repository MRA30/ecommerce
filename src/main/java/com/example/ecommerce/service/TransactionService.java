package com.example.ecommerce.service;


import com.example.ecommerce.dto.request.TransactionRequest;
import com.example.ecommerce.dto.request.TransactionUpdateRequest;
import com.example.ecommerce.dto.response.TransactionResponse;
import com.example.ecommerce.model.Transaction;

import org.springframework.data.domain.Page;

public interface TransactionService {

  Transaction save(Transaction transaction);

  Transaction findById(long id);

  void createTransaction(long userId, TransactionRequest transactionRequest);

  void updateTransaction(long id, TransactionUpdateRequest transactionUpdateRequest);

  TransactionResponse findByIdByCustomer(long userId, long id);

  TransactionResponse findByIdAdmin(long id);

  Page<TransactionResponse> findAllTransactionByCustomer(long userId, String status, int page,
      int size, String sortBy, String sortDir);

  Page<TransactionResponse> findAllTransactionByAdmin(String status, int page, int size,
      String sortBy, String sortDir);

}
