package com.nunegal.similarproducts.domain.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ProductNotFoundExceptionTest {
  
  @Test
  @DisplayName("Should create exception with correct message")
  void constructor_shouldCreateExceptionWithMessage() {
    String message = "Test error message";
    ProductNotFoundException exception = new ProductNotFoundException(message);
    
    assertEquals(message, exception.getMessage());
  }
  
  @Test
  @DisplayName("Should create exception with proper message")
  void mainProductNotFound_shouldCreateProperMessage() {
    String productId = "123";
    ProductNotFoundException exception =
        ProductNotFoundException.mainProductNotFound(productId);
    
    assertEquals("Main product not found: 123", exception.getMessage());
  }
  
  @Test
  @DisplayName("Should create exception with proper message")
  void noSimilarProducts_shouldCreateProperMessage() {
    String productId = "456";
    ProductNotFoundException exception =
        ProductNotFoundException.noSimilarProducts(productId);
    
    assertEquals("No similar products found for: 456", exception.getMessage());
  }
  
  @Test
  @DisplayName("Should create exception with proper message")
  void noAvailableSimilarProducts_shouldCreateProperMessage() {
    String productId = "789";
    ProductNotFoundException exception =
        ProductNotFoundException.noAvailableSimilarProducts(productId);
    
    assertEquals("No available similar products found for: 789", exception.getMessage());
  }
  
  @Test
  @DisplayName("Exception should be instance of RuntimeException")
  void shouldBeRuntimeException() {
    ProductNotFoundException exception =
        ProductNotFoundException.mainProductNotFound("999");
    
    assertInstanceOf(RuntimeException.class, exception);
  }
}