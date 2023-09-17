package com.shop.project.service;

import com.shop.project.dto.order.OrderFullDTO;
import com.shop.project.dto.order.ProductForOrderDTO;
import com.shop.project.models.CartItem;
import com.shop.project.models.Customer;
import com.shop.project.models.Order;
import com.shop.project.models.OrderItem;
import com.shop.project.models.Product;
import com.shop.project.repository.CartItemRepo;
import com.shop.project.repository.CustomerRepo;
import com.shop.project.repository.OrderItemRepo;
import com.shop.project.repository.OrderRepo;
import com.shop.project.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private OrderItemRepo orderItemRepo;

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

    public ResponseEntity<List<OrderFullDTO>> getListOfOrders(Long customerId) {
        Optional<List<Order>> orderByCustomerId = orderRepo.findOrderByCustomer_Id(customerId);
        if (orderByCustomerId.isPresent() && !orderByCustomerId.get().isEmpty()) {
            List<Order> orders = orderByCustomerId.get();
            List<OrderFullDTO> orderFullDTOS = new ArrayList<>();
            for (Order order : orders) {
                List<OrderItem> items = order.getOrderItems();
                orderFullDTOS.add(mapOrderToFullOrderDTO(order, items));
            }
            return ResponseEntity.ok().body(orderFullDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<OrderFullDTO> getOrder(Long customerId, Long orderId) {
        Optional<Order> orderOptional = orderRepo.findOrderByIdAndCustomer_Id(orderId, customerId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            OrderFullDTO orderFullDTO = mapOrderToFullOrderDTO(order, order.getOrderItems());
            return ResponseEntity.ok().body(orderFullDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private OrderFullDTO mapOrderToFullOrderDTO(Order order, List<OrderItem> orderItems) {
        List<ProductForOrderDTO> products = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            products.add(ProductForOrderDTO.builder()
                    .productName(orderItem.getProduct().getName())
                    .productId(orderItem.getProduct().getId())
                    .quantityPurchased(orderItem.getQuantity())
                    .priceForQuantity(orderItem.getTotalProductPrice())
                    .build());
        }
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return OrderFullDTO.builder()
                .id(order.getId())
                .orderCreatedAt(order.getCreatedAt().format(formatter))
                .customerId(order.getCustomer().getId())
                .totalPrice(order.getTotalPrice())
                .productList(products)
                .build();
    }


}
