package com.example.ecommerce.repository;

import com.example.ecommerce.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

  Address findById(long id);

  List<Address> findByUserId(long userId);

}
