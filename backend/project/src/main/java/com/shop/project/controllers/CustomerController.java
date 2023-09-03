package com.shop.project.controllers;

import com.shop.project.dto.CustomerDTO;
import com.shop.project.models.Customer;
import com.shop.project.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private final CustomerService service;
    @Autowired
    private final ModelMapper mapper;

    @GetMapping("/users")
    public List<CustomerDTO> getAll() {
        return service.getCustomers().stream()
                .map(this::mapCustomerToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = service.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(mapCustomerToDTO(customer));
        } else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("users/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id,
                                                      @RequestParam(required = false) String email,
                                                      @RequestParam(required = false) String phone,
                                                      @RequestParam(required = false) String password) {
        Customer customer = service.getCustomerById(id);
        if (customer != null) {
            service.updateCustomer(id, email, phone, password);
            return ResponseEntity.ok(mapCustomerToDTO(customer));
        } else
            return ResponseEntity.notFound().build();
    }


    private CustomerDTO mapCustomerToDTO(Customer customer) {
        return mapper.map(customer, CustomerDTO.class);
    }

    private Customer mapCustomerFromDTO(CustomerDTO customerDTO) {
        return mapper.map(customerDTO, Customer.class);
    }
}
