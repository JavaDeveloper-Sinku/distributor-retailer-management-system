package com.rishi.drms.mapper;

import com.rishi.drms.dto.request.InventoryRequest;
import com.rishi.drms.dto.response.InventoryResponse;
import com.rishi.drms.entity.Inventory;

import java.time.LocalDateTime;

public final class InventoryMapper {

    private InventoryMapper() {
    }

    public static Inventory toEntity(InventoryRequest request) {

        return Inventory.builder()
                .productId(request.getProductId())
                .availableStock(request.getAvailableStock())
                .reservedStock(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static InventoryResponse toResponse(Inventory inventory) {

        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .availableStock(inventory.getAvailableStock())
                .reservedStock(inventory.getReservedStock())
                .createdAt(inventory.getCreatedAt())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }
}