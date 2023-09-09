package com.shop.project.repository;

import com.shop.project.models.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducerRepo extends JpaRepository<Producer, Long> {
    @Query("SELECT p.name FROM Producer p")
    List<String> findAllNames();
    Optional<Producer> findProducerByName(String name);
}
