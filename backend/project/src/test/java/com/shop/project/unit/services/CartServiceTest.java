package com.shop.project.unit.services;

import com.shop.project.models.CartItem;
import com.shop.project.models.Customer;
import com.shop.project.models.Product;
import com.shop.project.repository.CartItemRepo;
import com.shop.project.repository.CustomerRepo;
import com.shop.project.repository.ProductRepo;
import com.shop.project.service.CartService;
import factory.ObjectsFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static factory.ObjectsFactory.generateCustomer;
import static factory.ObjectsFactory.generateProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class CartServiceTest {
    @InjectMocks
    private CartService cartService;
    @Mock
    private CartItemRepo cartItemRepo;
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private ProductRepo productRepo;

    @Test
    public void shouldFindCartProducts() {
        //Given
        Long customerId = 1L;
        Long productId = 2L;
        int quantity = 5;
        Customer customer = generateCustomer();
        Product product = generateProduct();
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(cartItemRepo.findCartByProductAndCustomer(product, customer)).thenReturn(Optional.empty());
        when(cartItemRepo.save(any())).thenReturn(null);
        //When
        ResponseEntity<String> response = cartService.addItemToCart(customerId, productId, quantity);
        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product added successfully", response.getBody());
    }

    @Test
    void addNewItemSuccess() {
        //Given
        Long customerId = 1L;
        Long productId = 2L;
        int quantity = 5;
        Customer customer = generateCustomer();
        Product product = generateProduct();
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(cartItemRepo.findCartByProductAndCustomer(product, customer)).thenReturn(Optional.empty());
        when(cartItemRepo.save(any())).thenReturn(null);
        //When
        ResponseEntity<String> response = cartService.addItemToCart(customerId, productId, quantity);
        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product added successfully", response.getBody());
    }

    @Test
    void addExistingItemToCartSuccess() {
        //Given
        Long customerId = 1L;
        Long productId = 2L;
        int quantity = 5;
        Customer customer = generateCustomer();
        Product product = generateProduct();
        CartItem existingCartItem = ObjectsFactory.generateCartItem();
        existingCartItem.setProduct(product);
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(cartItemRepo.findCartByProductAndCustomer(product, customer)).thenReturn(Optional.of(existingCartItem));
        when(cartItemRepo.save(any())).thenReturn(null);
        //When
        ResponseEntity<String> response = cartService.addItemToCart(customerId, productId, quantity);
        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product added successfully", response.getBody());
    }

    @Test
    void addItemToCartNotFoundCustomer() {
        //Given
        Long customerId = 1L;
        Long productId = 2L;
        int quantity = 5;
        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());
        //When
        ResponseEntity<String> response = cartService.addItemToCart(customerId, productId, quantity);
        //Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("No customer with id"));
    }

    @Test
    void updateCartItemQuantitySuccess() {
        //Given
        Long customerId = 1L;
        Long productId = 2L;
        int newQuantity = 5;
        CartItem existingCartItem = ObjectsFactory.generateCartItem();
        existingCartItem.setProduct(generateProduct());
        when(cartItemRepo.findCartItemByProduct_IdAndCustomer_Id(productId, customerId)).thenReturn(Optional.of(existingCartItem));
        when(cartItemRepo.save(any())).thenReturn(null);
        //When
        ResponseEntity<String> response = cartService.updateCartItemQuantity(newQuantity, productId, customerId);
        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cart item quantity updated successfully", response.getBody());
        assertEquals(newQuantity, existingCartItem.getQuantity());
    }

    @Test
    void updateCartItemQuantityInvalidQuantity() {
        //Given
        Long customerId = 1L;
        Long productId = 2L;
        int newQuantity = -1;
        CartItem existingCartItem = ObjectsFactory.generateCartItem();
        existingCartItem.setProduct(generateProduct());
        when(cartItemRepo.findCartItemByProduct_IdAndCustomer_Id(productId, customerId)).thenReturn(Optional.of(existingCartItem));
        //When
        ResponseEntity<String> response = cartService.updateCartItemQuantity(newQuantity, productId, customerId);
        //Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Quantity should be above 0"));
        assertEquals(existingCartItem.getQuantity(), existingCartItem.getQuantity()); // Quantity should remain unchanged
    }

    @Test
    void updateCartItemQuantityExceedsAvailableQuantity() {
        //Given
        Long customerId = 1L;
        Long productId = 2L;
        int newQuantity = 100000;
        CartItem existingCartItem = ObjectsFactory.generateCartItem();
        existingCartItem.setProduct(generateProduct());
        when(cartItemRepo.findCartItemByProduct_IdAndCustomer_Id(productId, customerId)).thenReturn(Optional.of(existingCartItem));
        //When
        ResponseEntity<String> response = cartService.updateCartItemQuantity(newQuantity, productId, customerId);
        //Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Unable to add quantity"));
        assertEquals(existingCartItem.getQuantity(), existingCartItem.getQuantity()); // Quantity should remain unchanged
    }

    @Test
    void updateCartItemQuantityCartItemNotFound() {
        //Given
        Long customerId = 1L;
        Long productId = 2L;
        int newQuantity = 5;
        when(cartItemRepo.findCartItemByProduct_IdAndCustomer_Id(productId, customerId)).thenReturn(Optional.empty());
        //When
        ResponseEntity<String> response = cartService.updateCartItemQuantity(newQuantity, productId, customerId);
        //Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("No cartItem with productId"));
    }

}
