package com.shop.project.controllers;

import com.shop.project.models.Customer;
import com.shop.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private  CustomerService service;
    @GetMapping("/user")
    public List<Customer> getAll() {
        return service.getCustomer();
    }
}
