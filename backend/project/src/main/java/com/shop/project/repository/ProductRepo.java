package com.shop.project.repository;

import com.shop.project.models.Category;
import com.shop.project.models.Producer;
import com.shop.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<List<Product>> findProductsByCategory(Category category);

    Optional<List<Product>> findProductsByProducer(Producer producer);

    Optional<List<Product>> findProductsByNameIgnoreCaseStartingWith(String name);

    Optional<List<Product>> findProductByCategoryAndProducer(Category category, Producer producer);

    Optional<List<Product>> findProductsByNameIgnoreCaseStartingWithAndCategory(String name, Category category);

    Optional<List<Product>> findProductsByNameIgnoreCaseStartingWithAndProducer(String name, Producer producer);

    Optional<List<Product>> findProductsByNameIgnoreCaseStartingWithAndCategoryAndProducer(String name, Category category, Producer producer);
}
