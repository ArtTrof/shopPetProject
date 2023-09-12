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
public class CartProductDTO {
    private Long cartId;
    private ProductToCartMiniDTO miniProductDTO;
    private int quantity;
    private double totalProductPrice;
}
