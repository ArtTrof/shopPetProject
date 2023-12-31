package com.shop.project.service;

import com.shop.project.dto.cart.CartProductDTO;
import com.shop.project.dto.cart.ProductToCartMiniDTO;
import com.shop.project.models.CartItem;
import com.shop.project.models.Customer;
import com.shop.project.models.Product;
import com.shop.project.repository.CartItemRepo;
import com.shop.project.repository.CustomerRepo;
import com.shop.project.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ProductRepo productRepo;

    public List<CartProductDTO> findCartProducts(Long customerId) {
        List<CartProductDTO> list = new ArrayList<>();
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if (customer == null) {
            return null;
        }
        CartItem cart = customer.getCartItem();
        if (cart == null) {
            return list;
        }
        Optional<List<CartItem>> optionalCarts = cartItemRepo.findByCustomer_Id(customerId);
        if (optionalCarts.isPresent()) {
            List<CartItem> cartItems = optionalCarts.get();
            if (!cartItems.isEmpty()) {
                for (CartItem cartItem : cartItems) {
                    list.add(getCartDTO(cartItem));
                }
            }
        }
        return list;
    }

    @Transactional
    public ResponseEntity<String> addItemToCart(Long customerId, Long productId, Integer quantity) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if (!customer.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("No customer with id %s", customerId));
        }
        Optional<Product> productOptional = productRepo.findById(productId);
        if (!productOptional.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("No product with id %s", productId));
        }
        Product product = productOptional.get();
        CartItem cartItem = cartItemRepo.findCartByProductAndCustomer(product, customer.get()).orElse(null);
        int quantityToAdd = 1;

        if (quantity != null) {
            if (quantity <= 0) {
                return ResponseEntity.badRequest().body("Quantity should be grater then 0");
            }
        }

        if (cartItem != null) {
            quantityToAdd = cartItem.getQuantity() + (quantity != null ? quantity : 1);
            cartItem.setQuantity(quantityToAdd);
        } else {
            if (quantity != null) {
                quantityToAdd = quantity;
            }
            cartItem = CartItem.builder().product(product).customer(customer.get()).quantity(quantityToAdd).build();
            if (canItemBeAdded(product.getQuantity(), quantityToAdd)) {
                cartItemRepo.save(cartItem);
                customer.get().setCartItem(cartItem);
                customerRepo.save(customer.get());
                return ResponseEntity.ok("Product added successfully");
            } else {
                return ResponseEntity.badRequest().body(String.format("Available quantity for product %s is %d", product.getName(), product.getQuantity()));
            }
        }

        if (canItemBeAdded(product.getQuantity(), quantityToAdd)) {
            cartItemRepo.save(cartItem);
            return ResponseEntity.ok("Product added successfully");
        } else {
            return ResponseEntity.badRequest().body(String.format("Available quantity for product %s is %d", product.getName(), product.getQuantity()));
        }
    }

    @Transactional
    public ResponseEntity<String> updateCartItemQuantity(Integer quantity, Long productId, Long customerId) {
        Optional<CartItem> cartItem = cartItemRepo.findCartItemByProduct_IdAndCustomer_Id(productId, customerId);
        if (cartItem.isPresent()) {
            if (quantity < cartItem.get().getProduct().getQuantity()) {
                if (quantity > 0) {
                    cartItem.get().setQuantity(quantity);
                    cartItemRepo.save(cartItem.get());
                    return ResponseEntity.ok().body("Cart item quantity updated successfully");
                } else {
                    return ResponseEntity.badRequest().body("Quantity should be above 0");
                }
            } else {
                return ResponseEntity.badRequest().body(String.format("Unable to add quantity:%s , because available items in stock is %s", quantity, cartItem.get().getProduct().getQuantity()));
            }
        } else {
            return ResponseEntity.badRequest().body(String.format("No cartItem with productId %s and customerId %s", productId, customerId));
        }
    }

    private boolean canItemBeAdded(int product, Integer quantity) {
        return product >= quantity;
    }

    private CartProductDTO getCartDTO(CartItem cartItem) {
        ProductToCartMiniDTO productDTO = getMiniProductDTO(cartItem);
        return CartProductDTO.builder()
                .cartId(cartItem.getId())
                .miniProductDTO(productDTO)
                .quantity(cartItem.getQuantity())
                .totalProductPrice((double) cartItem.getQuantity() * productDTO.getPrice())
                .build();
    }

    private ProductToCartMiniDTO getMiniProductDTO(CartItem cartItem) {
        return ProductToCartMiniDTO.builder()
                .id(cartItem.getProduct().getId())
                .price(cartItem.getProduct().getPrice())
                .isAvailable(cartItem.getProduct().isAvailable())
                .name(cartItem.getProduct().getName())
                .quantity(cartItem.getProduct().getQuantity())
                .build();
    }
    @Transactional
    public ResponseEntity<String> deleteItem(Long customerId, Long productId) {
        Optional<CartItem> cartItem = cartItemRepo.findCartItemByProduct_IdAndCustomer_Id(productId, customerId);
        if (cartItem.isPresent()) {
            cartItemRepo.delete(cartItem.get());
            return ResponseEntity.ok().body("Item was deleted from cart");
        } else {
            return ResponseEntity.badRequest().body(String.format("Cart item for customer ud:%s or productId:%s not found", customerId, productId));
        }
    }
}
