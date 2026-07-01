package com.rishi.drms.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String name;

    private String sku;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
