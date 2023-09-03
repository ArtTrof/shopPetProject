package com.shop.project.controllers;

import com.shop.project.dto.CustomerLoginDTO;
import com.shop.project.models.Customer;
import com.shop.project.service.CustomerService;
import com.shop.project.util.ThrownException;
import com.shop.project.util.validators.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.shop.project.util.ErrorUtil.returnErrorToClient;
import static com.shop.project.util.validators.ValidationErrorResponse.getValidationErrors;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerValidator validator;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/signup")
    public ResponseEntity<String> login(@RequestBody @Validated CustomerLoginDTO customerDTO, BindingResult bindingResult) {
        var customer = mapCustomerFromDTO(customerDTO);
        validator.validate(customer, bindingResult);
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        try {
            customerService.saveNewCustomer(customer);
            return ResponseEntity.ok("Customer registered successfully");
        } catch (ThrownException e) {
            return ResponseEntity.badRequest().body(String.format(e.getMessage()+",CODE: 400"));
        }
    }

    private Customer mapCustomerFromDTO(CustomerLoginDTO dto) {
        return mapper.map(dto, Customer.class);
    }
}
