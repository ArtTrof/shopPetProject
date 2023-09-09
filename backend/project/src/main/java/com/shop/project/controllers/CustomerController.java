package com.shop.project.controllers;

import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.dto.customer.CustomerUpdateDTO;
import com.shop.project.models.Customer;
import com.shop.project.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.shop.project.util.validators.ValidationErrorResponse.getValidationErrors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private final CustomerService service;
    @Autowired
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = service.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(mapCustomerToDTO(customer));
        } else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(
//            @AuthenticationPrincipal Customer customer,
            @PathVariable Long id,
            @RequestBody @Valid CustomerUpdateDTO dto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }
        return service.updateCustomer(id, dto);
    }

    private CustomerDTO mapCustomerToDTO(Customer customer) {
        var res = mapper.map(customer, CustomerDTO.class);
        res.setRoles(customer.getRoles().iterator().next().toString());
        return res;
    }

    private Customer mapCustomerFromDTO(CustomerDTO customerDTO) {
        return mapper.map(customerDTO, Customer.class);
    }
}
