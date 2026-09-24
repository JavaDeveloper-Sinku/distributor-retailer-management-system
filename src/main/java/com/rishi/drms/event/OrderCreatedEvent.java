package com.rishi.drms.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;

    private String orderNumber;

    private String retailerId;

    private List<OrderCreatedItemEvent> items;
}