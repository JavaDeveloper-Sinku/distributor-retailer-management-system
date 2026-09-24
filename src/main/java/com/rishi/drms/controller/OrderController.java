package com.rishi.drms.controller;

import com.rishi.drms.common.response.ApiResponse;
import com.rishi.drms.dto.request.OrderRequest;
import com.rishi.drms.dto.response.OrderResponse;
import com.rishi.drms.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody OrderRequest request) {

        OrderResponse response = orderService.createOrder(request);

        ApiResponse<OrderResponse> apiResponse =
                ApiResponse.<OrderResponse>builder()
                        .success(true)
                        .message("Order created successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {

        List<OrderResponse> orders = orderService.getAllOrders();

        ApiResponse<List<OrderResponse>> response =
                ApiResponse.<List<OrderResponse>>builder()
                        .success(true)
                        .message("Orders fetched successfully")
                        .data(orders)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
            @PathVariable Long id) {

        OrderResponse order = orderService.getOrderById(id);

        ApiResponse<OrderResponse> response =
                ApiResponse.<OrderResponse>builder()
                        .success(true)
                        .message("Order fetched successfully")
                        .data(order)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderRequest request) {

        OrderResponse order = orderService.updateOrder(id, request);

        ApiResponse<OrderResponse> response =
                ApiResponse.<OrderResponse>builder()
                        .success(true)
                        .message("Order updated successfully")
                        .data(order)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(
            @PathVariable Long id) {

        orderService.deleteOrder(id);

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Order deleted successfully")
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }
}