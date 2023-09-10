package com.shop.project.dto.cart;

import com.shop.project.dto.product.ProductFullDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDTO {
    private Long cartId;
    private ProductToCartMiniDTO miniProductDTO;
    private int quantity;
}
