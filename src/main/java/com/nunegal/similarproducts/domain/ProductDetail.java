package com.nunegal.similarproducts.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {
  private String id;
  private String name;
  private double price;
  private boolean availability;
}