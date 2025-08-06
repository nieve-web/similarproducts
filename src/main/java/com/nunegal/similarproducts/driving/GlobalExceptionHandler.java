package com.nunegal.similarproducts.driving;

import com.nunegal.similarproducts.domain.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleProductNotFound(ProductNotFoundException ex) {
    Map<String, Object> errorBody = new HashMap<>();
    errorBody.put("timestamp", LocalDateTime.now());
    errorBody.put("status", HttpStatus.NOT_FOUND.value());
    errorBody.put("error", "Not Found");
    errorBody.put("message", ex.getMessage());
    
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
  }
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Internal server error: " + ex.getMessage());
  }

}