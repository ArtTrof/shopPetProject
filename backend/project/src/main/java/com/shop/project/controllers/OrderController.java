package com.shop.project.controllers;

import com.shop.project.dto.order.OrderFullDTO;
import com.shop.project.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/order")
@Api(tags = "! Orders endpoints")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "Checkout", description = "Checkout order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/checkout/{customerId}")
    public ResponseEntity<String> checkout(
            @PathVariable Long customerId
    ) {
        return orderService.postCheckout(customerId);
    }

    @Operation(summary = "Get orders", description = "Get list of orders for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping("/getOrders/{customerId}")
    public ResponseEntity<List<OrderFullDTO>> getListOfOrder(
            @PathVariable Long customerId
    ) {
        return orderService.getListOfOrders(customerId);
    }
    @Operation(summary = "Get order by id for customer", description = "Get single order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping("/getOrders/{customerId}/{orderId}")
    public ResponseEntity<OrderFullDTO> getListOfOrder(
            @PathVariable Long customerId,
            @PathVariable Long orderId
    ) {
        return orderService.getOrder(customerId,orderId);
    }
}
