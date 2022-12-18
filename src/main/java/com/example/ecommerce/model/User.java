package com.example.ecommerce.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private long id;

  @Column(name = "fullName", nullable = false)
  private String fullName;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "phone_number", nullable = false, unique = true)
  private String phoneNumber;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "is_verified", nullable = false, columnDefinition = "boolean default false")
  private boolean isVerified;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
  private Role role;

  @Column(name = "status", nullable = false, columnDefinition = "boolean default false")
  private boolean status;

  @Column(name = "verification_code")
  private String verificationCode;

  @Column(name = "verification_code_expired")
  private LocalDateTime verificationCodeExpired;

}
