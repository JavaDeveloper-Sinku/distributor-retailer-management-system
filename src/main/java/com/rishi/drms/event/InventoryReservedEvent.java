package com.rishi.drms.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReservedEvent {

    private Long orderId;

    private String orderNumber;

    private String retailerId;

    private Long productId;

    private Integer quantity;
}