package com.example.ecommerce.repository;

import com.example.ecommerce.model.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query("SELECT t FROM Transaction t WHERE t.user.id = ?1")
  Page<Transaction> findAllTransactionByCustomer(long userId, Pageable pageable);

  @Query("SELECT t FROM Transaction t WHERE t.user.id = ?1 AND t.status = ?2")
  Page<Transaction> findTransactionByCustomerAndStatus(long userId, int status, Pageable pageable);

  @Query("SELECT t FROM Transaction t")
  Page<Transaction> findAllTransactionByAdmin(Pageable pageable);

  @Query("SELECT t FROM Transaction t WHERE t.status = ?1")
  Page<Transaction> findAllTransactionByAdminAndStatus(long status, Pageable pageable);
}
