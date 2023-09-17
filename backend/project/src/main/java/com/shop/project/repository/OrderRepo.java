package com.shop.project.repository;

import com.shop.project.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    Optional<List<Order>> findOrderByCustomer_Id(Long customerId);
    Optional<Order> findOrderByIdAndCustomer_Id(Long orderId,Long CustomerId);
}
