package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderDto> getOrders(String token);

    OrderDto getOrderById(Integer id, String token);

    OrderDto createOrder(String token, String accountNumber, String password);

    OrderDto payOrder(Integer id, String token);
}
