package com.shop.project.service;

import com.shop.project.models.CartItem;
import com.shop.project.models.Customer;
import com.shop.project.models.Order;
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
                List<Product> productIds = new ArrayList<>();
                Map<Long, Integer> productQuantity = new HashMap<>();
                for (CartItem cartItem : cartItems.get()) {
                    Product product = cartItem.getProduct();
                    int quantity = product.getQuantity();
                    productQuantity.put(product.getId(), productQuantity.getOrDefault(product.getId(), 0) + quantity);
                    product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                    productRepo.save(product);
                    productIds.add(product);
                }
                double totalPrice = 0;
                for (Product product : productIds) {
                    int quantity = productQuantity.get(product.getId());
                    totalPrice += product.getPrice() * quantity;
                }
                order.setProductIds(productIds);
                order.setTotalPrice(totalPrice);
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
