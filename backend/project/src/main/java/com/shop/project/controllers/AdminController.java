package com.shop.project.controllers;

import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.dto.customer.CustomerRoleUpdateDTO;
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
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CustomerService service;
    @Autowired
    private ModelMapper mapper;

    //    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public List<CustomerDTO> getAll() {
        return service.getCustomers().stream()
                .map(this::mapCustomerToDTO)
                .collect(Collectors.toList());
    }

    //    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = service.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(mapCustomerToDTO(customer));
        } else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateCustomerRole(@PathVariable Long id, @RequestBody CustomerRoleUpdateDTO dto) {
        service.updateCustomerRole(id, dto.getRoles());
        return ResponseEntity.ok().build();
    }

    private CustomerDTO mapCustomerToDTO(Customer customer) {
        return mapper.map(customer, CustomerDTO.class);
    }
}
