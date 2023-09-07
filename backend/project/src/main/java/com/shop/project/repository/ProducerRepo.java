package com.shop.project.repository;

import com.shop.project.models.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepo extends JpaRepository<Producer, Long> {
    Optional<Producer> findProducerByName(String name);
}
