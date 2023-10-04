package com.shop.project.controllers;

import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.dto.customer.CustomerEmailDTO;
import com.shop.project.dto.customer.CustomerPasswordDTO;
import com.shop.project.dto.customer.CustomerUpdateDTO;
import com.shop.project.models.Customer;
import com.shop.project.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api(tags = "! Customer endpoints")
public class CustomerController {
    @Autowired
    private final CustomerService service;
    @Autowired
    private final ModelMapper mapper;

    @Operation(summary = "Get customer", description = "Get customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "No customer found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = service.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(mapCustomerToDTO(customer));
        } else
            return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update customer", description = "Update customer data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success update"),
            @ApiResponse(responseCode = "400", description = "Wrong data input")
    })
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

    @Operation(summary = "Get customer email to change password", description = "Get email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Wrong email/non-existing")
    })
    @PostMapping("/inputEmail")
    public ResponseEntity<?> postCustomerEmail(@RequestBody @Validated CustomerEmailDTO customerEmailDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }
        return service.checkEmailAndSendLinkToResetPassword(customerEmailDTO.getEmail());
    }

    @Operation(summary = "Change customer password", description = "Change password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid password")
    })
    @PutMapping("/newPassword/{uniqueId}")
    public ResponseEntity<?> updateCustomerPassword(@PathVariable String uniqueId,
                                                    @RequestBody @Validated CustomerPasswordDTO customerEmailDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }
        return service.updateCustomerPassword(uniqueId, customerEmailDTO.getPassword());
    }

    private CustomerDTO mapCustomerToDTO(Customer customer) {
        var res = mapper.map(customer, CustomerDTO.class);
        res.setRoles(customer.getRoles().iterator().next().toString());
        return res;
    }
}
