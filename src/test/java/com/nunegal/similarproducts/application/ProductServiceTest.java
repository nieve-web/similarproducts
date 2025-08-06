package com.nunegal.similarproducts.application;

import com.nunegal.similarproducts.domain.ProductDetail;
import com.nunegal.similarproducts.domain.exception.ProductNotFoundException;
import com.nunegal.similarproducts.driven.ProductClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import static org.mockito.Mockito.*;

class ProductServiceTest {
  
  private ProductClient productClient;
  private ProductService productService;
  
  @BeforeEach
  void setUp() {
    productClient = mock(ProductClient.class);
    productService = new ProductService(productClient);
  }
  
  @Test
  @DisplayName("Should return similar product details when all responses are successful")
  void shouldReturnProductDetails_whenAllCallsSucceed() {
    String productId = "1";
    List<String> similarIds = List.of("2", "3");
    
    ProductDetail product2 = new ProductDetail("2", "Shoes", 19.99, true);
    ProductDetail product3 = new ProductDetail("3", "Hat", 9.99, false);
    
    when(productClient.getSimilarProductIds(productId)).thenReturn(Flux.fromIterable(similarIds));
    when(productClient.getProductDetail("2")).thenReturn(Mono.just(product2));
    when(productClient.getProductDetail("3")).thenReturn(Mono.just(product3));
    
    StepVerifier.create(productService.getSimilarProducts(productId))
        .expectNext(product2)
        .expectNext(product3)
        .verifyComplete();
    
    verify(productClient).getSimilarProductIds(productId);
    verify(productClient).getProductDetail("2");
    verify(productClient).getProductDetail("3");
  }
  
  @Test
  @DisplayName("Should skip product details with error and continue with others")
  void shouldSkipProductErrorDetailsAndContinue() {
    String productId = "1";
    List<String> similarIds = List.of("2", "3");
    
    ProductDetail product2 = new ProductDetail("2", "Shoes", 19.99, true);
    
    when(productClient.getSimilarProductIds(productId)).thenReturn(Flux.fromIterable(similarIds));
    when(productClient.getProductDetail("2")).thenReturn(Mono.just(product2));
    when(productClient.getProductDetail("3")).thenReturn(Mono.error(new RuntimeException("Timeout")));
    
    StepVerifier.create(productService.getSimilarProducts(productId))
        .expectNext(product2)
        .verifyComplete();
    
    verify(productClient).getProductDetail("3");
  }
  
  @Test
  @DisplayName("Should propagate ProductNotFoundException when fetching similar product IDs fails")
  void shouldThrowProductNotFound_whenSimilarIdsFails() {
    String productId = "1";
    
    when(productClient.getSimilarProductIds(productId))
        .thenReturn(Flux.error(new RuntimeException("Service unavailable")));
    
    StepVerifier.create(productService.getSimilarProducts(productId))
        .expectErrorMatches(e ->
            e instanceof ProductNotFoundException &&
                e.getMessage().equals("Main product not found: 1"))
        .verify();
  }
}
