package com.example.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "products")
public class Product extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "stock", nullable = false, columnDefinition = "INT DEFAULT 0")
  private int stock;

  @Column(name = "sold", nullable = false, columnDefinition = "INT DEFAULT 0")
  private int sold;

  @Column(name = "price", nullable = false, columnDefinition = "NUMERIC DEFAULT 0")
  private double price;

  @Column(name = "description", nullable = false, columnDefinition = "TEXT")
  private String description;

}
