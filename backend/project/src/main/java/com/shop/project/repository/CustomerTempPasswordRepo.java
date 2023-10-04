package com.shop.project.repository;

import com.shop.project.models.CustomerTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTempPasswordRepo extends JpaRepository<CustomerTmp, String> {
}
