package com.example.ecommerce.repository;

import com.example.ecommerce.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  User findById(long id);

  @Query("SELECT u FROM User u WHERE u.email LIKE %?1% OR u.fullName LIKE %?1% OR u.phoneNumber LIKE %?1% AND u.role.id = 1")
  Page<User> findAdminByEmailOrFullNameOrPhoneNumber(String keyword, Pageable pageable);

  @Query("SELECT u FROM User u WHERE u.email LIKE %?1% OR u.fullName LIKE %?1% OR u.phoneNumber LIKE %?1% AND u.role.id = 1 ")
  Page<User> findCustomerByEmailOrFullNameOrPhoneNumber(String keyword, Pageable pageable);

  @Query("SELECT u FROM User u WHERE u.email LIKE %?1% OR u.fullName LIKE %?1% OR u.phoneNumber LIKE %?1% AND u.status = ?2 AND u.role.id = 1")
  Page<User> findAdminByEmailOrFullNameOrPhoneNumberAndStatus(String keyword, boolean status, Pageable pageable);

  @Query("SELECT u FROM User u WHERE u.email LIKE %?1% OR u.fullName LIKE %?1% OR u.phoneNumber LIKE %?1% AND u.status = ?2 AND u.role.id = 2")
  Page<User> findCustomerByEmailOrFullNameOrPhoneNumberAndStatus(String keyword, boolean status, Pageable pageable);

}
