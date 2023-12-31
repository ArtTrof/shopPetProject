package com.shop.project.dto.product;

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
public class ProductFullDTO {
    private Long id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private double price;
    private boolean isAvailable;
    private int quantity;
    private String category;
    private String producer;
}
