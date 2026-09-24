package com.rishi.drms.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedItemEvent {

    private Long productId;

    private Integer quantity;
}