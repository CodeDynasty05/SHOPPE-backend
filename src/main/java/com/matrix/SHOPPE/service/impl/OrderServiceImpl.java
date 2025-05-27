package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.OrderRepository;
import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.exception.ResourceNotFoundException;
import com.matrix.SHOPPE.exception.UserNotFoundException;
import com.matrix.SHOPPE.jwt.JwtService;
import com.matrix.SHOPPE.mapper.OrderMapper;
import com.matrix.SHOPPE.model.dto.BasketDto;
import com.matrix.SHOPPE.model.dto.OrderDto;
import com.matrix.SHOPPE.model.dto.TransactionDto;
import com.matrix.SHOPPE.model.entity.Order;
import com.matrix.SHOPPE.model.entity.OrderItem;
import com.matrix.SHOPPE.model.entity.OrderStatus;
import com.matrix.SHOPPE.model.entity.User;
import com.matrix.SHOPPE.service.BasketService;
import com.matrix.SHOPPE.service.OrderService;
import com.matrix.SHOPPE.service.PaymentService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final BasketService basketService;
    private final PaymentService paymentService;

    @Override
    public List<OrderDto> getOrders(String token) {
        User user = getUserFromToken(token);
        log.info("Fetching orders for user with username: {}", user.getUsername());
        return orderRepository.findByUserId(user.getId()).stream().map(orderMapper::orderToOrderDto).toList();
    }

    @Override
    public OrderDto getOrderById(Integer id, String token) {
        log.info("Fetching order with ID: {}", id);
        User user = getUserFromToken(token);
        List<Order> orders = orderRepository.findByUserId(user.getId());
        Order order = orders.stream().filter(o -> o.getId().equals(id)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto createOrder(String token, String accountNumber, String password) {
        User user = getUserFromToken(token);
        List<BasketDto> basket = basketService.getBasketById(user.getId());

        if (basket.isEmpty()) {
            throw new IllegalStateException("Cannot create order with empty basket");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        List<OrderItem> orderItems = new ArrayList<>();
        Double totalAmount = 0D;

        for (BasketDto basketItem : basket) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(basketItem.getProduct());
            orderItem.setQuantity(basketItem.getQuantity());
            orderItems.add(orderItem);

            Double itemTotal = basketItem.getProduct().getPrice()
                    * basketItem.getQuantity() * (1 - basketItem.getProduct().getDiscount() / 100);
            totalAmount += itemTotal;
        }
        TransactionDto transactionDto = paymentService.createPayment(totalAmount, accountNumber, user.getUsername(), password);

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setTransactionId((int) (long) transactionDto.getId());
        Order savedOrder = orderRepository.save(order);

        basket.forEach(basketDto -> basketService.delete(basketDto.getId()));


        log.info("Created order for user: {}, with total amount: {}", user.getUsername(), totalAmount);
        return orderMapper.orderToOrderDto(savedOrder);
    }

    @Override
    public OrderDto payOrder(Integer id, String token) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        paymentService.confirmPayment(order.getTransactionId());
        order.setStatus(OrderStatus.PAID);
        return orderMapper.orderToOrderDto(orderRepository.save(order));
    }


    private User getUserFromToken(String token) {
        Claims claims = jwtService.validateToken(token.substring("bearer".length()).trim());
        String username = claims.get("sub", String.class);
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}