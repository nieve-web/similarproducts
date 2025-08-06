package com.nunegal.similarproducts.driving;

import com.nunegal.similarproducts.application.port.ProductUseCase;
import com.nunegal.similarproducts.domain.ProductDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebFluxTest(ProductController.class)
@Import(ProductControllerTest.TestConfig.class)
class ProductControllerTest {
  
  @Autowired
  private WebTestClient webTestClient;
  
  @Autowired
  private ProductUseCase productUseCase;
  
  @TestConfiguration
  static class TestConfig {
    @Bean
    ProductUseCase productUseCase() {
      return mock(ProductUseCase.class);
    }
  }
  
  @Test
  @DisplayName("Should return similar products when service returns data")
  void shouldReturnSimilarProducts_whenValidProductIdGiven() {
    String productId = "1";
    List<ProductDetail> products = List.of(
        new ProductDetail("2", "Shoes", 19.99, true),
        new ProductDetail("3", "Hat", 9.99, false)
    );
    
    when(productUseCase.getSimilarProducts(productId)).thenReturn(Flux.fromIterable(products));
  
    webTestClient.get()
        .uri("/product/{productId}/similar", productId)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(ProductDetail.class)
        .hasSize(2)
        .contains(products.toArray(new ProductDetail[0]));
    
    Mockito.verify(productUseCase).getSimilarProducts(productId);
  }
  
  @Test
  @DisplayName("Should return 404 when product is not found")
  void shouldReturnEmptyList_whenNoSimilarProducts() {
    String productId = "2";
    when(productUseCase.getSimilarProducts(productId)).thenReturn(Flux.empty());
  
    webTestClient.get()
        .uri("/product/{productId}/similar", productId)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(ProductDetail.class)
        .hasSize(0);
  }
}
