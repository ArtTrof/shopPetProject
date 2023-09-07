package com.shop.project.repository;

import com.shop.project.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    Optional<Category> findCategoryByName(String name);
}
