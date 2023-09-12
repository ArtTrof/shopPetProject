package com.shop.project.controllers;

import com.shop.project.dto.cart.CartProductDTO;
import com.shop.project.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/cart")
public class CartItemController {
    @Autowired
    private CartService cartService;

    @Operation(summary = "Get cart items", description = "Get cart items info as products in it and quantity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/getItems/{customerId}")
    public ResponseEntity<List<CartProductDTO>> getCartProducts(
            @PathVariable(name = "customerId", required = true) Long customerId) {
        List<CartProductDTO> products = cartService.findCartProducts(customerId);
        if (products != null) {
            return ResponseEntity.ok().body(products);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/putItem/{id}")
    public ResponseEntity<String> addItem(
            @PathVariable(name = "id",required = true)Long customerId,
            @RequestParam(name = "productId", required = true) Long productId,
            @RequestParam(name = "quantity", required = false) Integer quantity) {
        return cartService.addItemToCart(customerId,productId, quantity);
    }
}