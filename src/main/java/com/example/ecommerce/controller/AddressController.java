package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.AddressRequest;
import com.example.ecommerce.service.AddressService;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.util.DefaultResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

  private final AddressService addressService;

  private final UserService userService;

  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @PostMapping
  public ResponseEntity<DefaultResponse<?>> createAddress(
      @Valid @RequestBody AddressRequest addressRequest) {
    long userId = userService.user().getId();
    addressService.createAddress(userId, addressRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.CREATED.value()));
  }

  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @GetMapping
  public ResponseEntity<DefaultResponse<?>> findAllAddressByUserId() {
    long userId = userService.user().getId();
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, addressService.findAllByUserId(userId),
            HttpStatus.OK.value()));
  }

  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @GetMapping("/{id}")
  public ResponseEntity<DefaultResponse<?>> findAddressById(@PathVariable("id") long id) {
    long userId = userService.user().getId();
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, addressService.findAddressById(id, userId),
            HttpStatus.OK.value()));
  }

  @PreAuthorize("hasRole('ROLE_CUSTOMER')")
  @PutMapping("/{id}")
  public ResponseEntity<DefaultResponse<?>> updateAddress(@PathVariable("id") long id,
      @Valid @RequestBody AddressRequest addressRequest) {
    long userId = userService.user().getId();
    addressService.updateAddress(userId, id, addressRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.OK.value()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<DefaultResponse<?>> deleteAddressById(@PathVariable("id") long id) {
    long userId = userService.user().getId();
    addressService.deleteAddress(id, userId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new DefaultResponse<>("Success", false, null, HttpStatus.OK.value()));
  }

}
