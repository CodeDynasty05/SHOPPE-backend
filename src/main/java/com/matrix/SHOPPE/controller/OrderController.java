package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.dto.OrderDto;
import com.matrix.SHOPPE.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders(@RequestHeader(name = "Authorization", required = false) String token) {
        return orderService.getOrders(token);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Integer id, @RequestHeader(name = "Authorization", required = false) String token) {
        return orderService.getOrderById(id, token);
    }

    @PostMapping("/buy/{accountNumber}")
    public OrderDto buyProduct(@RequestHeader(name = "Authorization", required = false) String token, @PathVariable String accountNumber, @RequestParam String password) {
        return orderService.createOrder(token, accountNumber, password);
    }

    @PutMapping("/pay/{id}")
    public OrderDto payOrder(@PathVariable Integer id, @RequestHeader(name = "Authorization", required = false) String token) {
        return orderService.payOrder(id, token);
    }
}
