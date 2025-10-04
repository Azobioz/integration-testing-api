package com.azobioz.api.controller;

import com.azobioz.api.dto.NewOrderProductRequest;
import com.azobioz.api.dto.OrderRequest;
import com.azobioz.api.dto.OrderResponse;
import com.azobioz.api.module.Order;
import com.azobioz.api.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{id}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getAllUserOrdersById(id));
    }


    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable Long id,
                                                     @RequestBody OrderRequest request) {

        OrderResponse order = orderService.createOrder(id, request);
        return ResponseEntity.ok(order);
    }

}
