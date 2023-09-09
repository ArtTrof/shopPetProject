package com.shop.project.repository;

import com.shop.project.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query("SELECT c.name FROM Category c")
    List<String> findAllNames();

    Optional<Category> findCategoryByName(String name);
}
