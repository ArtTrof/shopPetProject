package com.shop.project.service;

import com.shop.project.dto.cart.CartProductDTO;
import com.shop.project.dto.cart.ProductToCartMiniDTO;
import com.shop.project.models.cart.Cart;
import com.shop.project.repository.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    public List<CartProductDTO> findCartProducts(Long customerId) {
        List<CartProductDTO> list = new ArrayList<>();
        Optional<List<Cart>> optionalCarts = cartRepo.findByCustomer_Id(customerId);
        if (optionalCarts.isPresent()) {
            var carts = optionalCarts.get();
            for (Cart cart : carts) {
                list.add(CartProductDTO.builder()
                        .cartId(cart.getId())
                        .miniProductDTO(ProductToCartMiniDTO.builder()
                                .id(cart.getProduct().getId())
                                .price(cart.getProduct().getPrice())
                                .isAvailable(cart.getProduct().isAvailable())
                                .name(cart.getProduct().getName())
                                .quantity(cart.getProduct().getQuantity())
                                .build())
                        .quantity(cart.getQuantity())
                        .build()
                );
            }
            return list;
        } else {
            return List.of();
        }
    }
}
