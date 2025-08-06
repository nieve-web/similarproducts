package com.nunegal.similarproducts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ProductDetailTest {
  
  @Test
  void shouldCreateProductDetailCorrectly() {
    ProductDetail product = new ProductDetail("1", "Shirt", 9.99, true);
    
    assertThat(product.getId()).isEqualTo("1");
    assertThat(product.getName()).isEqualTo("Shirt");
    assertThat(product.getPrice()).isEqualTo(9.99);
    assertThat(product.isAvailability()).isTrue();
  }
  
  @Test
  void shouldModifyProductDetailWithSetters() {
    ProductDetail product = new ProductDetail();
    product.setId("2");
    product.setName("Jeans");
    product.setPrice(19.99);
    product.setAvailability(false);
    
    assertThat(product.getId()).isEqualTo("2");
    assertThat(product.getName()).isEqualTo("Jeans");
    assertThat(product.getPrice()).isEqualTo(19.99);
    assertThat(product.isAvailability()).isFalse();
  }
  
  @Test
  void shouldUseEqualsAndHashCode() {
    ProductDetail product1 = new ProductDetail("1", "Shirt", 9.99, true);
    ProductDetail product2 = new ProductDetail("1", "Shirt", 9.99, true);
    
    assertThat(product1).isEqualTo(product2);
    assertThat(product1.hashCode()).isEqualTo(product2.hashCode());
  }
  
  @Test
  void shouldGenerateToString() {
    ProductDetail product = new ProductDetail("1", "Shirt", 9.99, true);
    
    String result = product.toString();
    assertThat(result).contains("1", "Shirt", "9.99", "true");
  }
}
