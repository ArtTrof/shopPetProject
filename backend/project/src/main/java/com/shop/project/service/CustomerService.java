package com.shop.project.service;

import com.shop.project.models.Customer;
import com.shop.project.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private  CustomerRepo customerRepo;

    public List<Customer> getCustomer(){
        return customerRepo.findAll();
    }
}
