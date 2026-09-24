package com.rishi.drms.mapper;

import com.rishi.drms.dto.request.OrderRequest;
import com.rishi.drms.dto.response.OrderItemResponse;
import com.rishi.drms.dto.response.OrderResponse;
import com.rishi.drms.entity.Order;
import com.rishi.drms.entity.OrderItem;

import java.util.List;

public final class OrderMapper {

    private OrderMapper() {
    }

    public static Order toEntity(OrderRequest request) {

        Order order = new Order();

        order.setRetailerId(request.getRetailerId());

        List<OrderItem> items = request.getItems()
                .stream()
                .map(itemRequest -> {

                    OrderItem item = new OrderItem();

                    item.setProductId(itemRequest.getProductId());
                    item.setQuantity(itemRequest.getQuantity());
                    item.setPrice(itemRequest.getPrice());
                    item.setOrder(order);

                    return item;
                })
                .toList();

        order.setItems(items);

        return order;
    }

    public static OrderResponse toResponse(Order order) {

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .retailerId(order.getRetailerId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}