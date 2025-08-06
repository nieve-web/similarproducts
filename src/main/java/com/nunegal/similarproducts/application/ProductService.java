package com.nunegal.similarproducts.application;

import com.nunegal.similarproducts.application.port.ProductUseCase;
import com.nunegal.similarproducts.domain.ProductDetail;
import com.nunegal.similarproducts.domain.exception.ProductNotFoundException;
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
        .onErrorMap(e -> ProductNotFoundException.mainProductNotFound(productId))
        .switchIfEmpty(Flux.error(ProductNotFoundException.noSimilarProducts(productId)))
        .flatMap(id ->
            productClient.getProductDetail(id)
                .timeout(Duration.ofSeconds(3))
                .onErrorResume(e -> Mono.empty()) // Missing similar products are ignored
        )
        .switchIfEmpty(Flux.error(ProductNotFoundException.noAvailableSimilarProducts(productId))
        );
  }
}