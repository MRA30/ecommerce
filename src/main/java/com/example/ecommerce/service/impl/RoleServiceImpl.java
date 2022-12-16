package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Role;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.service.RoleService;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Override
  public Role findById(long id) {
    return roleRepository.findById(id);
  }
}
