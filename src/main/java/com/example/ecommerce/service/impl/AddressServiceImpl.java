package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.request.AddressRequest;
import com.example.ecommerce.dto.response.AddressResponse;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.service.AddressService;
import com.example.ecommerce.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  private final UserService userService;

  private void save(Address address) {
    addressRepository.save(address);
  }

  private AddressResponse convertToResponse(Address address) {
    return AddressResponse.builder()
        .id(address.getId())
        .street(address.getAddress())
        .build();
  }

  @Override
  public void createAddress(long userId, AddressRequest addressRequest) {
    User user = userService.findById(userId);
    Address address = Address.builder()
        .address(addressRequest.getStreet())
        .user(user)
        .build();
    save(address);
  }

  @Override
  public void updateAddress(long userId, long id, AddressRequest addressRequest) {
    Address address = findById(id);
    if (address == null) {
      throw new BusinessException("id", "Address not found", HttpStatus.BAD_REQUEST);
    } else {
      if (address.getUser().getId() != userId) {
        throw new BusinessException("id", "Address not found", HttpStatus.BAD_REQUEST);
      }
      address.setAddress(addressRequest.getStreet());
      save(address);
    }
  }

  @Override
  public void deleteAddress(long userId, long id) {
    Address address = findById(id);
    if (address == null) {
      throw new BusinessException("id", "Address not found", HttpStatus.BAD_REQUEST);
    } else {
      if (address.getUser().getId() != userId) {
        throw new BusinessException("id", "Address not found", HttpStatus.BAD_REQUEST);
      }
      addressRepository.delete(address);
    }
  }

  @Override
  public List<AddressResponse> findAllByUserId(long userId) {
    List<Address> addresses = addressRepository.findByUserId(userId);
    return addresses.stream().map(this::convertToResponse).collect(Collectors.toList());
  }

  @Override
  public Address findById(long id) {
    return addressRepository.findById(id);
  }

  @Override
  public AddressResponse findAddressById(long userId, long id) {
    Address address = findById(id);
    if (address == null) {
      throw new BusinessException("id", "Address not found", HttpStatus.BAD_REQUEST);
    } else {
      if (address.getUser().getId() != userId) {
        throw new BusinessException("id", "Address not found", HttpStatus.BAD_REQUEST);
      }
      return convertToResponse(address);
    }
  }
}
