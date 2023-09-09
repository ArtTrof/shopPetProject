package com.shop.project.controllers;

import com.shop.project.dto.customer.CustomerLoginDTO;
import com.shop.project.dto.customer.CustomerRegistrationDTO;
import com.shop.project.models.Customer;
import com.shop.project.service.CustomerService;
import com.shop.project.util.ThrownException;
import com.shop.project.util.validators.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.shop.project.util.validators.ValidationErrorResponse.getValidationErrors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerValidator validator;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Validated CustomerRegistrationDTO customerDTO,
                                         BindingResult bindingResult) {
        var customer = mapCustomerFromDTO(customerDTO);
        validator.validate(customer, bindingResult);
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        try {
            customerService.saveNewCustomer(customer);
            return ResponseEntity.ok("Customer registered successfully");
        } catch (ThrownException e) {
            return ResponseEntity.badRequest().body(String.format(e.getMessage() + ",CODE: 400"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated CustomerLoginDTO customerDTO,
                                        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(customerDTO.getEmail(), customerDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }

    private Customer mapCustomerFromDTO(CustomerRegistrationDTO dto) {
        return mapper.map(dto, Customer.class);
    }
}
