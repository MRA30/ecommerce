package com.example.ecommerce.util;


import lombok.Getter;

@Getter
public class DefaultResponse<T> {

  private final String message;

  private final boolean error;

  private final int status;

  private final T data;

  public DefaultResponse(String message, boolean error, T data, int status) {
    this.message = message;
    this.error = error;
    this.data = data;
    this.status = status;
  }


}
