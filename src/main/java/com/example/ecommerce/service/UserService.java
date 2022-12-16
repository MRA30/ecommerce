package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.CustomerRegister;
import com.example.ecommerce.dto.request.LoginRequest;
import com.example.ecommerce.dto.request.UserUpdateProfile;
import com.example.ecommerce.dto.request.UpdateUserRequest;
import com.example.ecommerce.dto.request.UserRequest;
import com.example.ecommerce.dto.response.UserResponse;
import com.example.ecommerce.model.User;

public interface UserService {

  User findById(long id);

  UserResponse findUserById(long id);

  void registerCustomer(CustomerRegister customerRegister);

  String login(LoginRequest loginrequest);

  void updateProfile(UserUpdateProfile userUpdateProfile);

  void createAdmin(UserRequest userRequest);

  void updateUser(long id, UpdateUserRequest updateUserRequest);

}
