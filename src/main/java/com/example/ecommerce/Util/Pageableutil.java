package com.example.ecommerce.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class Pageableutil {

  public static Pageable createPageable(int page, int size, String sortBy, String sortDir) {
    Pageable pageable;
    if (Objects.equals(sortDir, "asc")) {
      pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
    } else {
      pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
    }
    return pageable;
  }

}
