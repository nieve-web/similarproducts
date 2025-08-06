package com.nunegal.similarproducts.driving;

import com.nunegal.similarproducts.domain.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {
  
  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
  
  @Test
  @DisplayName("Should return 404 and proper body when ProductNotFoundException is thrown")
  void shouldReturn404Response_whenProductNotFoundExceptionThrown() {
    String productId = "999";
    ProductNotFoundException ex = new ProductNotFoundException(productId);

    ResponseEntity<Map<String, Object>> response = handler.handleProductNotFound(ex);
 
    assertThat(response.getStatusCode().value()).isEqualTo(404);
    Map<String, Object> body = response.getBody();
    assertThat(body).isNotNull();
    assertThat(body.get("status")).isEqualTo(404);
    assertThat(body.get("error")).isEqualTo("Not Found");
    assertThat(body.get("message")).isEqualTo(productId);
    assertThat(body.get("timestamp")).isNotNull();
  }
  
  @Test
  @DisplayName("Should return 500 and error message when generic exception is thrown")
  void shouldReturn500Response_whenGenericExceptionThrown() {
    Exception ex = new RuntimeException("Unexpected error");

    ResponseEntity<String> response = handler.handleGenericException(ex);
 
    assertThat(response.getStatusCode().value()).isEqualTo(500);
    assertThat(response.getBody()).contains("Internal server error: Unexpected error");
  }
}

