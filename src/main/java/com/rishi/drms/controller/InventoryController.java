package com.rishi.drms.controller;

import com.rishi.drms.common.response.ApiResponse;
import com.rishi.drms.dto.request.InventoryRequest;
import com.rishi.drms.dto.response.InventoryResponse;
import com.rishi.drms.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryResponse>> createInventory(
            @Valid @RequestBody InventoryRequest request) {

        InventoryResponse response =
                inventoryService.createInventory(request);

        ApiResponse<InventoryResponse> apiResponse =
                ApiResponse.<InventoryResponse>builder()
                        .success(true)
                        .message("Inventory created successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAllInventory() {

        List<InventoryResponse> inventory =
                inventoryService.getAllInventory();

        ApiResponse<List<InventoryResponse>> response =
                ApiResponse.<List<InventoryResponse>>builder()
                        .success(true)
                        .message("Inventory fetched successfully")
                        .data(inventory)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventoryById(
            @PathVariable Long id) {

        InventoryResponse inventory =
                inventoryService.getInventoryById(id);

        ApiResponse<InventoryResponse> response =
                ApiResponse.<InventoryResponse>builder()
                        .success(true)
                        .message("Inventory fetched successfully")
                        .data(inventory)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventoryByProductId(
            @PathVariable Long productId) {

        InventoryResponse inventory =
                inventoryService.getInventoryByProductId(productId);

        ApiResponse<InventoryResponse> response =
                ApiResponse.<InventoryResponse>builder()
                        .success(true)
                        .message("Inventory fetched successfully")
                        .data(inventory)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryResponse>> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequest request) {

        InventoryResponse inventory =
                inventoryService.updateInventory(id, request);

        ApiResponse<InventoryResponse> response =
                ApiResponse.<InventoryResponse>builder()
                        .success(true)
                        .message("Inventory updated successfully")
                        .data(inventory)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInventory(
            @PathVariable Long id) {

        inventoryService.deleteInventory(id);

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Inventory deleted successfully")
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }
}