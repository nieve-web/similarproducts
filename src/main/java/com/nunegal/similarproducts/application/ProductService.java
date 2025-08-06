package com.nunegal.similarproducts.application;

import com.nunegal.similarproducts.application.port.ProductUseCase;
import com.nunegal.similarproducts.domain.ProductDetail;
import com.nunegal.similarproducts.driven.ProductClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
  
  private final ProductClient productClient;
  
  @Override
  public Flux<ProductDetail> getSimilarProducts(String productId) {
    return productClient.getSimilarProductIds(productId)
        .onErrorResume(e -> Flux.empty())
        .flatMap(id ->
            productClient.getProductDetail(id)
                .timeout(Duration.ofSeconds(3))
                .onErrorResume(e -> Mono.empty())
        );
  }
}