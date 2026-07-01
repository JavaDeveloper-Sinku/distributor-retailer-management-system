package com.rishi.drms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductUpdateRequest {

    @NotNull(message = "Product name is required")
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private Boolean active;

}
