package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.service.AddressService;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  @Override
  public Address findById(long id) {
    return addressRepository.findById(id);
  }
}
