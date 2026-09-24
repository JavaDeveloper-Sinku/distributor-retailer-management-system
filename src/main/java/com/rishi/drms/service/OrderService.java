package com.rishi.drms.service;

import com.rishi.drms.dto.request.OrderRequest;
import com.rishi.drms.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse updateOrder(Long id, OrderRequest request);

    void deleteOrder(Long id);
}