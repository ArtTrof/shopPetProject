package com.shop.project.dto.order;

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
public class ProductForOrderDTO {
    private Long productId;
    private String productName;
    private int quantityPurchased;
    private double priceForQuantity;
}
