package com.rishi.drms.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryResponse {

    private Long id;
    private Long productId;
    private Integer availableStock;
    private Integer reservedStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}