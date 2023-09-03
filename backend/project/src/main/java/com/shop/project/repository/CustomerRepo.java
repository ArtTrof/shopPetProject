package com.shop.project.repository;

import com.shop.project.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerById(Long id);
    Optional<Customer> findCustomerByFirstName(Customer customer);
    @Query("FROM Customer where email=:email")
    Optional<Customer> findByEmail(@Param("email") String email);
}
