package com.nunegal.similarproducts.driven;

import com.nunegal.similarproducts.domain.ProductDetail;
import com.nunegal.similarproducts.domain.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ProductClient {
  
  private final WebClient webClient;
  
  public Flux<String> getSimilarProductIds(String productId) {
    return webClient.get()
        .uri("/{id}/similarids", productId)
        .retrieve()
        .bodyToMono(String[].class)
        .flatMapMany(ids -> Flux.fromIterable(Arrays.asList(ids)))
        .onErrorMap(e -> new ProductNotFoundException(productId));
  }
  
  public Mono<ProductDetail> getProductDetail(String productId) {
    return webClient.get()
        .uri("/{id}", productId)
        .retrieve()
        .bodyToMono(ProductDetail.class)
        .onErrorMap(e -> new ProductNotFoundException(productId));
  }
}