package com.example.ecommerce.exception;

import com.example.ecommerce.util.DefaultResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HandlingException {

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity<DefaultResponse<?>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DefaultResponse<>(
        "Error",
        true,
        errors,
        HttpStatus.UNPROCESSABLE_ENTITY.value()
    ));
  }

  @ExceptionHandler(value = BusinessException.class)
  @ResponseBody
  public ResponseEntity<Object> handleBusinessException(BusinessException be) {
    Map<String, String> errors = new HashMap<>();
    errors.put(be.getField(), be.getMessage());
    return ResponseEntity.status(be.getHttpStatus())
        .body(new
            DefaultResponse<>(
            "Error",
            true,
            errors,
            be.getHttpStatus().value()
        ));
  }

  @ExceptionHandler(value = IOException.class)
  @ResponseBody
  public ResponseEntity<Object> handleIOException(IOException ioe) {
    Map<String, String> errors = new HashMap<>();
    errors.put("IOException", ioe.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new
            DefaultResponse<>(
            "Error",
            true,
            errors,
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        ));
  }

}
