package com.nunegal.similarproducts.driven;

import com.nunegal.similarproducts.domain.ProductDetail;
import com.nunegal.similarproducts.domain.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductClientTest {
  
  @Mock
  private WebClient webClient;
  
  @Mock
  private RequestHeadersUriSpec requestHeadersUriSpec;
  
  @Mock
  private ResponseSpec responseSpec;
  
  @InjectMocks
  private ProductClient productClient;
  
  @BeforeEach
  void setUp() {
    when(webClient.get()).thenReturn(requestHeadersUriSpec);
    when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersUriSpec);
    when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
  }
  
  @Test
  @DisplayName("getSimilarProductIds when successful request should return flux of product IDs")
  void getSimilarProductIds_shouldReturnFluxOfIds_whenRequestIsSuccessful() {
    String[] mockIds = {"1", "2", "3"};
    when(responseSpec.bodyToMono(String[].class))
        .thenReturn(Mono.just(mockIds));
    
    Flux<String> result = productClient.getSimilarProductIds("123");
    
    StepVerifier.create(result)
        .expectNext("1", "2", "3")
        .verifyComplete();
    
    verify(requestHeadersUriSpec).uri("/{id}/similarids", "123");
  }
  
  @Test
  @DisplayName("getSimilarProductIds when request fails should throw ProductNotFoundException")
  void getSimilarProductIds_shouldThrowProductNotFoundException_whenRequestFails() {
    when(responseSpec.bodyToMono(String[].class))
        .thenReturn(Mono.error(new RuntimeException("Error")));
    
    StepVerifier.create(productClient.getSimilarProductIds("123"))
        .expectError(ProductNotFoundException.class)
        .verify();
  }
  
  @Test
  @DisplayName("getProductDetail when product exists should return product details")
  void getProductDetail_shouldReturnProductDetail_whenRequestIsSuccessful() {
    ProductDetail mockDetail = new ProductDetail("1", "Product 1", 10.0, true);
    when(responseSpec.bodyToMono(ProductDetail.class))
        .thenReturn(Mono.just(mockDetail));
    
    Mono<ProductDetail> result = productClient.getProductDetail("1");
    
    StepVerifier.create(result)
        .expectNext(mockDetail)
        .verifyComplete();
    
    verify(requestHeadersUriSpec).uri("/{id}", "1");
  }
  
  @Test
  @DisplayName("getProductDetail when request fails should throw ProductNotFoundException")
  void getProductDetail_shouldThrowProductNotFoundException_whenRequestFails() {
    when(responseSpec.bodyToMono(ProductDetail.class))
        .thenReturn(Mono.error(new RuntimeException("Error")));
    
    StepVerifier.create(productClient.getProductDetail("1"))
        .expectError(ProductNotFoundException.class)
        .verify();
  }
  
  @Test
  @DisplayName("getProductDetail when product not found should throw ProductNotFoundException")
  void getProductDetail_shouldThrowProductNotFoundException_whenProductNotFound() {
    when(responseSpec.bodyToMono(ProductDetail.class))
        .thenReturn(Mono.error(new ProductNotFoundException("999")));
    
    StepVerifier.create(productClient.getProductDetail("999"))
        .expectError(ProductNotFoundException.class)
        .verify();
  }
}