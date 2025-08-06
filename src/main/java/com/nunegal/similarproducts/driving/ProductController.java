package com.nunegal.similarproducts.driving;

import com.nunegal.similarproducts.application.port.ProductUseCase;
import com.nunegal.similarproducts.domain.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
  
  private final ProductUseCase productUseCase;
  
  @GetMapping("/{productId}/similar")
  public Flux<ProductDetail> getSimilarProducts(@PathVariable String productId) {
    return productUseCase.getSimilarProducts(productId);
  }
}