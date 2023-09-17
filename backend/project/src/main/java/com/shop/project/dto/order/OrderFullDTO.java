package com.shop.project.dto.order;

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
public class OrderFullDTO {
    private Long id;
    private Long customerId;
    private List<ProductForOrderDTO> productList;
    private String orderCreatedAt;
    private double totalPrice;
}
