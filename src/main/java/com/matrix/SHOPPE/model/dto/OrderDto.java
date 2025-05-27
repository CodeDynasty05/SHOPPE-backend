package com.matrix.SHOPPE.model.dto;

import com.matrix.SHOPPE.model.entity.OrderItem;
import com.matrix.SHOPPE.model.entity.OrderStatus;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class OrderDto {
    private Integer id;
    private Double totalAmount;
    private Integer transactionId;
    private OrderStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private List<OrderItem> orderItems;
}
