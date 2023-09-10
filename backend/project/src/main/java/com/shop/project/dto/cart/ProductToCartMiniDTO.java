package com.shop.project.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductToCartMiniDTO {
    private Long id;
    private String name;
    private double price;
    private boolean isAvailable;
    private int quantity;
}
