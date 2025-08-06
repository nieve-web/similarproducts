package com.nunegal.similarproducts.domain.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String productId) {
    super("Product not found: " + productId);
  }
}
