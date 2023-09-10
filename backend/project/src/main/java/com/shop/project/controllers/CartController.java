package com.shop.project.controllers;

import com.shop.project.dto.cart.CartProductDTO;
import com.shop.project.models.Product;
import com.shop.project.models.cart.Cart;
import com.shop.project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartProductDTO>> getCartProducts(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(cartService.findCartProducts(customerId));
    }
}
