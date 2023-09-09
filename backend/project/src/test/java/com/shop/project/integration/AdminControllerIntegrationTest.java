package com.shop.project.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.project.dto.customer.CustomerDTO;
import com.shop.project.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getAll() throws Exception {
        //Given
        List<CustomerDTO> customerDTOList = customerService.getCustomers();
        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //Then
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        List<CustomerDTO> resList = mapper.readValue(content, new TypeReference<>() {
        });
        assertEquals(customerDTOList, resList);
    }
}
