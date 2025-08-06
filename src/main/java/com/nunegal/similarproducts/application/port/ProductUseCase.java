package com.nunegal.similarproducts.application.port;

import com.nunegal.similarproducts.domain.ProductDetail;
import reactor.core.publisher.Flux;

public interface ProductUseCase {
  Flux<ProductDetail> getSimilarProducts(String productId);
}