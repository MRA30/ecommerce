package com.example.ecommerce;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final String field;

  private final HttpStatus httpStatus;

  public BusinessException(String field, String message, HttpStatus httpStatus) {
    super(message);
    this.field = field;
    this.httpStatus = httpStatus;
  }

}
