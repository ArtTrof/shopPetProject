package com.shop.project.controllers;

import com.shop.project.dto.cart.CartProductDTO;
import com.shop.project.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/cart")
@Api(tags = "! Cart item endpoints")
public class CartItemController {
    @Autowired
    private CartService cartService;

    @Operation(summary = "Get cart items", description = "Get cart items info as products in it and quantity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/getCartItems/{customerId}")
    public ResponseEntity<List<CartProductDTO>> getCartProducts(
            @PathVariable(name = "customerId") Long customerId) {
        List<CartProductDTO> products = cartService.findCartProducts(customerId);
        if (products != null) {
            return ResponseEntity.ok().body(products);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Put item to cart", description = "Put item to cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/addCartItem/{customerId}")
    public ResponseEntity<String> addItem(
            @PathVariable(name = "customerId") Long customerId,
            @RequestParam(name = "productId", required = true) Long productId,
            @RequestParam(name = "quantity", required = false) Integer quantity) {
        return cartService.addItemToCart(customerId, productId, quantity);
    }

    @Operation(summary = "Update item quantity", description = "Update item quantity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/updateCartItem/{customerId}")
    public ResponseEntity<String> updateItemQuantity(
            @PathVariable Long customerId,
            @RequestParam(name = "productId", required = true) Long productId,
            @RequestParam(name = "quantity", required = true) Integer quantity
    ) {
        return cartService.updateCartItemQuantity(quantity, productId, customerId);
    }

    @Operation(summary = "Delete item ", description = "Delete item in cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success delete"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @DeleteMapping("/deleteCartItem/{customerId}")
    public ResponseEntity<String> deleteItemFromCart(
            @PathVariable Long customerId,
            @RequestParam(name = "productId", required = true) Long productId
    ) {
        return cartService.deleteItem(customerId, productId);
    }
}
