package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.ChangePasswordRequest;
import com.example.ecommerce.dto.request.CustomerRegister;
import com.example.ecommerce.dto.request.LoginRequest;
import com.example.ecommerce.dto.request.ResetPasswordRequest;
import com.example.ecommerce.dto.request.UpdateProfileRequest;
import com.example.ecommerce.dto.request.UpdateUserRequest;
import com.example.ecommerce.dto.request.UserRequest;
import com.example.ecommerce.dto.response.UserResponse;
import com.example.ecommerce.model.User;

public interface UserService {

  User user();

  User findById(long id);

  User findByEmail(String email);

  UserResponse findUserById(long id);

  void registerCustomer(CustomerRegister customerRegister);

  String login(LoginRequest loginrequest);

  void createAdmin(UserRequest userRequest);

  UserResponse profile(long userId);

  void updateUser(long id, UpdateUserRequest updateUserRequest);

  void updateProfile(long userId, UpdateProfileRequest updateProfileRequest);

  String forgotPassword(String email);

  void resetPassword(String token, ResetPasswordRequest resetPasswordRequest);

  void changePassword(long userId, ChangePasswordRequest changePasswordRequest);

}
