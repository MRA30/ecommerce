package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.AddressRequest;
import com.example.ecommerce.dto.response.AddressResponse;
import com.example.ecommerce.model.Address;

import java.util.List;

public interface AddressService {

  void createAddress(long userId, AddressRequest addressRequest);

  void updateAddress(long userId, long id, AddressRequest addressRequest);

  void deleteAddress(long userId, long id);

  List<AddressResponse> findAllByUserId(long userId);

  Address findById(long id);

  AddressResponse findAddressById(long userId, long id);

}
