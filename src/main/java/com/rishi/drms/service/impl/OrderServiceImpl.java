package com.rishi.drms.service.impl;

import com.rishi.drms.dto.request.OrderRequest;
import com.rishi.drms.dto.response.OrderResponse;
import com.rishi.drms.entity.Order;
import com.rishi.drms.entity.OrderItem;
import com.rishi.drms.exception.ResourceNotFoundException;
import com.rishi.drms.mapper.OrderMapper;
import com.rishi.drms.repository.OrderRepository;
import com.rishi.drms.service.OrderService;
import com.rishi.drms.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        Order order = OrderMapper.toEntity(request);

        BigDecimal totalAmount = order.getItems()
                .stream()
                .map(item ->
                        item.getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOrderNumber(generateOrderNumber());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id
                        )
                );

        return OrderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Long id, OrderRequest request) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id
                        )
                );

        order.setRetailerId(request.getRetailerId());

        order.getItems().clear();

        request.getItems().forEach(itemRequest -> {

            OrderItem item = new OrderItem();

            item.setProductId(itemRequest.getProductId());
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(itemRequest.getPrice());
            item.setOrder(order);

            order.getItems().add(item);
        });

        BigDecimal totalAmount = order.getItems()
                .stream()
                .map(item ->
                        item.getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        Order updatedOrder = orderRepository.save(order);

        return OrderMapper.toResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id
                        )
                );

        orderRepository.delete(order);
    }

    private String generateOrderNumber() {

        return "ORD-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}