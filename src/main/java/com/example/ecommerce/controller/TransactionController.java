package com.example.ecommerce.controller;

import com.example.ecommerce.service.UserService;
import com.example.ecommerce.util.DefaultResponse;
import com.example.ecommerce.dto.request.TransactionRequest;
import com.example.ecommerce.dto.request.TransactionUpdateRequest;
import com.example.ecommerce.dto.response.TransactionResponse;
import com.example.ecommerce.service.TransactionService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/transactions")
@RestController
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  private final UserService userService;

  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @PostMapping
  public ResponseEntity<DefaultResponse<?>> createTransaction(
      @Valid @RequestBody TransactionRequest transactionRequest) {
    long userId = userService.user().getId();
    transactionService.createTransaction(userId, transactionRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.CREATED.value()));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<DefaultResponse<?>> updateTransaction(@PathVariable("id") long id,
      @Valid @RequestBody TransactionUpdateRequest transactionUpdateRequest) {
    transactionService.updateTransaction(id, transactionUpdateRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.OK.value()));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping
  public ResponseEntity<DefaultResponse<Page<TransactionResponse>>> findAllTransactionByAdmin(
      @RequestParam(defaultValue = "all") String status, @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {
    Page<TransactionResponse> transactionResponses = transactionService.findAllTransactionByAdmin(
        status, page, size, sortBy, sortDir);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, transactionResponses, HttpStatus.OK.value()));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<DefaultResponse<TransactionResponse>> findByIdTransactionByAdmin(
      @PathVariable("id") long id) {
    TransactionResponse transactionResponse = transactionService.findByIdAdmin(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, transactionResponse, HttpStatus.OK.value()));
  }


  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @GetMapping("/customer")
  public ResponseEntity<DefaultResponse<Page<TransactionResponse>>> findAllTransactionByCustomer(
      @RequestParam(defaultValue = "all") String status, @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {
    long userId = userService.user().getId();
    Page<TransactionResponse> transactionResponses = transactionService.findAllTransactionByCustomer(
        userId, status, page, size, sortBy, sortDir);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, transactionResponses, HttpStatus.OK.value()));
  }

  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @GetMapping("/customer/{id}")
  public ResponseEntity<DefaultResponse<TransactionResponse>> findByIdTransactionByCustomer(
      @PathVariable("id") long id) {
    long userId = userService.user().getId();
    TransactionResponse transactionResponse = transactionService.findByIdByCustomer(userId, id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, transactionResponse, HttpStatus.OK.value()));
  }

}
