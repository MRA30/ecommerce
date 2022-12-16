package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.request.LoginRequest;
import com.example.ecommerce.exception.BusinessException;
import com.example.ecommerce.dto.request.CustomerRegister;
import com.example.ecommerce.dto.request.UpdateUserRequest;
import com.example.ecommerce.dto.request.UserRequest;
import com.example.ecommerce.dto.request.UserUpdateProfile;
import com.example.ecommerce.dto.response.UserResponse;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.RoleService;
import com.example.ecommerce.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final RoleService roleService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private UserResponse mappeUserToUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .fullName(user.getFullName())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .roleId(user.getRole().getId())
        .rolename(user.getRole().getName())
        .isVerified(user.isVerified() ? "Verified" : "Not Verified")
        .status(user.isStatus() ? "Active" : "Inactive")
        .build();
  }


  @Override
  public User findById(long id) {
    return userRepository.findById(id);
  }

  @Override
  public UserResponse findUserById(long id) {
    User user = findById(id);
    if (user != null) {
      return mappeUserToUserResponse(user);
    } else {
      throw new BusinessException("id", "User not found", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public void registerCustomer(CustomerRegister customerRegister) {
    Role role = roleService.findById(2);
    User user = User.builder()
        .role(role)
        .fullName(customerRegister.getFullName())
        .email(customerRegister.getEmail())
        .phoneNumber(customerRegister.getPhoneNumber())
        .password(bCryptPasswordEncoder.encode(customerRegister.getPassword()))
        .status(true)
        .build();
    userRepository.save(user);
  }

  @Override
  public String login(LoginRequest loginrequest) {
    return null;
  }

  @Override
  public void updateProfile(UserUpdateProfile userUpdateProfile) {
    // TODO GET PROFILE
    User user = findById(1);
    user.setFullName(userUpdateProfile.getFullName());
    user.setPhoneNumber(userUpdateProfile.getPhoneNumber());
    user.setEmail(userUpdateProfile.getEmail());
    userRepository.save(user);

  }

  @Override
  public void createAdmin(UserRequest userRequest) {
    String password = UUID.randomUUID().toString().substring(0, 8);
    Role role = roleService.findById(1);
    User user = User.builder()
        .role(role)
        .fullName(userRequest.getFullName())
        .email(userRequest.getEmail())
        .phoneNumber(userRequest.getPhoneNumber())
        .isVerified(true)
        .status(true)
        .password(bCryptPasswordEncoder.encode(password))
        .build();
    userRepository.save(user);
  }

  @Override
  public void updateUser(long id, UpdateUserRequest updateUserRequest) {
    User user = findById(id);
    if (user != null) {
      user.setFullName(updateUserRequest.getFullName());
      user.setEmail(updateUserRequest.getEmail());
      user.setPhoneNumber(updateUserRequest.getPhoneNumber());
      user.setStatus(updateUserRequest.isStatus());
      userRepository.save(user);
    } else {
      throw new BusinessException("id", "User not found", HttpStatus.BAD_REQUEST);
    }
  }


}
