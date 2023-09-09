package com.shop.project.repository;

import com.shop.project.models.Category;
import com.shop.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<List<Product>> findProductByCategory(Category category);
    Optional<List<Product>> findProductByNameStartsWith(String name);
}
