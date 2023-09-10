package com.shop.project.repository;

import com.shop.project.models.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<List<Cart>> findByCustomer_Id(Long customerId);
}
