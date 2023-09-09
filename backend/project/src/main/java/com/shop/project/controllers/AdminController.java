package com.shop.project.controllers;

import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.models.Customer;
import com.shop.project.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Operation(summary = "Get list of users", description = "Returns list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get")
    })
    @GetMapping("/users")
    public List<CustomerDTO> getAll() {
        return service.getCustomers();
    }

    //    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get user by id", description = "Returns user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get"),
            @ApiResponse(responseCode = "404", description = "Not found user with such id")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = service.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(mapCustomerToDTO(customer));
        } else
            return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update user roles with id", description = "Update user role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update")
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateCustomerRole(@PathVariable Long id, @RequestParam String role) {
        try {
            service.updateCustomerRole(id, role);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + ",CODE:400");
        }
    }

    private CustomerDTO mapCustomerToDTO(Customer customer) {
        var res = mapper.map(customer, CustomerDTO.class);
        res.setRoles(customer.getRoles().iterator().next().toString());
        return res;
    }
}
