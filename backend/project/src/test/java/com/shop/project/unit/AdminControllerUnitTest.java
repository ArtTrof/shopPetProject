package com.shop.project.unit;

import com.shop.project.controllers.AdminController;
import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.models.Customer;
import com.shop.project.models.Role;
import com.shop.project.service.CustomerService;
import factory.CustomerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static factory.CustomerFactory.generateCustomer;
import static factory.CustomerFactory.mapCustomerToDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminControllerUnitTest {
    @InjectMocks
    private AdminController adminController;
    @Mock
    private CustomerService customerService;
    @Mock
    private ModelMapper mapper;

    @Test
    public void getAll() {
        //Given
        List<CustomerDTO> customers = List.of(mapCustomerToDTO(generateCustomer()), mapCustomerToDTO(generateCustomer()));
        //When
        when(adminController.getAll()).thenReturn(customers);
        //Then
        assertEquals(customers, adminController.getAll());
    }

    @Test
    public void getCustomerById() {
        //Given
        Customer customer = generateCustomer();
        long id = 1L;
        customer.setId(id);
        var mappedCustomer = mapCustomerToDTO(customer);
        //When
        when(customerService.getCustomerById(id)).thenReturn(customer);
        when(mapper.map(any(), any())).thenReturn(mappedCustomer);
        //Then
        ResponseEntity<CustomerDTO> response = adminController.getCustomerById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mappedCustomer, response.getBody());
    }

}
