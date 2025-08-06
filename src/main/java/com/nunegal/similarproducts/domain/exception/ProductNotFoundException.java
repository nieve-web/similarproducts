package com.nunegal.similarproducts.domain.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String message) {
    super(message);
  }
  
  public static ProductNotFoundException mainProductNotFound(String productId) {
    return new ProductNotFoundException("Main product not found: " + productId);
  }
  
  public static ProductNotFoundException noSimilarProducts(String productId) {
    return new ProductNotFoundException("No similar products found for: " + productId);
  }
  
  public static ProductNotFoundException noAvailableSimilarProducts(String productId) {
    return new ProductNotFoundException("No available similar products found for: " + productId);
  }
}