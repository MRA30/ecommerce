package com.example.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private long id;

  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private User user;

  @Column(name = "address", nullable = false)
  private String address;

  @OneToOne
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private Order order;

  @Column(name = "total_price", nullable = false)
  private double totalPrice;

  @Column(name = "status", nullable = false)
  private String status;

}
