package com.shop.project.service;

import com.shop.project.dto.order.OrderFullDTO;
import com.shop.project.models.CartItem;
import com.shop.project.models.Customer;
import com.shop.project.models.Order;
import com.shop.project.models.OrderItem;
import com.shop.project.models.Product;
import com.shop.project.repository.CartItemRepo;
import com.shop.project.repository.CustomerRepo;
import com.shop.project.repository.OrderRepo;
import com.shop.project.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CartItemRepo cartItemRepo;

    @Transactional
    public ResponseEntity<String> postCheckout(Long customerId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if (customer.isPresent()) {
            Optional<List<CartItem>> cartItems = cartItemRepo.findByCustomer_Id(customerId);
            if (cartItems.isPresent() && !cartItems.get().isEmpty()) {
                Order order = new Order();
                order.setCustomer(customer.get());
                double totalPrice = 0;
                List<OrderItem> orderItems = new ArrayList<>();
                for (CartItem cartItem : cartItems.get()) {
                    Product product = cartItem.getProduct();
                    int quantity = cartItem.getQuantity();
                    double productTotalPrice = product.getPrice() * quantity;
                    product.setQuantity(product.getQuantity() - quantity);
                    productRepo.save(product);
                    OrderItem orderItem = OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(quantity)
                            .totalProductPrice(productTotalPrice)
                            .build();
                    orderItems.add(orderItem);
                    totalPrice += productTotalPrice;
                }
                order.setOrderItems(orderItems);
                order.setTotalPrice(totalPrice);
                order.setCreatedAt(LocalDateTime.now());

                orderRepo.save(order);
                cartItemRepo.deleteCartItemByCustomer_Id(customerId);
                return ResponseEntity.ok().body("Checkout success");
            } else {
                return ResponseEntity.badRequest().body(String.format("Cart for customer with id: %s is empty", customerId));
            }
        } else {
            return ResponseEntity.badRequest().body(String.format("No customer with id %s", customerId));
        }
    }
}
