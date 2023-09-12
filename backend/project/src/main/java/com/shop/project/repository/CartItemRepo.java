package com.shop.project.repository;

import com.shop.project.models.CartItem;
import com.shop.project.models.Customer;
import com.shop.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Optional<List<CartItem>> findByCustomer_Id(Long customerId);

    Optional<CartItem> findCartByProductAndCustomer(Product product, Customer customer);
}
