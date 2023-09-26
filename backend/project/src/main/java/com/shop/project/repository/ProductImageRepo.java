package com.shop.project.repository;

import com.shop.project.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
    Optional<List<ProductImage>> findAllByProduct_Id(Long productId);
}
