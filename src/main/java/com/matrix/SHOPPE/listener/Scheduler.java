package com.matrix.SHOPPE.listener;

import com.matrix.SHOPPE.Repository.OrderRepository;
import com.matrix.SHOPPE.model.entity.Order;
import com.matrix.SHOPPE.model.entity.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Baku")
    public void scheduleCron() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            switch (order.getStatus()) {
                case OrderStatus.PENDING -> order.setStatus(OrderStatus.SHIPPED);
                case OrderStatus.SHIPPED -> order.setStatus(OrderStatus.DELIVERED);
            }
            orderRepository.save(order);
        }
    }
}

