package com.shop.project.unit;

import com.shop.project.controllers.AdminController;
import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.models.Customer;
import com.shop.project.models.Role;
import com.shop.project.service.CustomerService;
import com.shop.project.util.ThrownException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static factory.CustomerFactory.generateCustomer;
import static factory.CustomerFactory.mapCustomerToDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerUnitTest {
    @InjectMocks
    private AdminController adminController;
    @Mock
    private CustomerService customerService;
    @Mock
    private ModelMapper mapper;

    @Test
    public void testGetAll() {
        //Given
        List<CustomerDTO> customers = List.of(mapCustomerToDTO(generateCustomer()), mapCustomerToDTO(generateCustomer()));
        //When
        when(adminController.getAll()).thenReturn(customers);
        //Then
        assertEquals(customers, adminController.getAll());
    }

    @Test
    public void testGetCustomerById() {
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

    @Test
    public void testUpdateCustomerId() {
        //Given
        long id = 1;
        String roles = Set.of(Role.USER).toString();
        //When
        doNothing().when(customerService).updateCustomerRole(id, roles);
        //Act
        ResponseEntity<String> response = adminController.updateCustomerRole(id, roles);
        //Then
        verify(customerService, times(1)).updateCustomerRole(id, roles);
        assert response.getStatusCode().equals(HttpStatus.OK);
    }

    @Test
    public void testUpdateCustomerIdFailure() {
        //Given
        long id = 1;
        String roles = Set.of(Role.USER).toString();
        //When
        doThrow(new ThrownException("No customer with such id")).when(customerService).updateCustomerRole(id, roles);
        //Act
        ResponseEntity<String> response = adminController.updateCustomerRole(id, roles);
        //Then
        verify(customerService, times(1)).updateCustomerRole(id, roles);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("No customer with such id,CODE:400"));
    }

}
