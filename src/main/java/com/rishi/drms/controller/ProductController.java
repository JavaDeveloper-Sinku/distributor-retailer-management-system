package com.rishi.drms.controller;


import com.rishi.drms.common.response.ApiResponse;
import com.rishi.drms.dto.ProductRequest;
import com.rishi.drms.dto.ProductResponse;
import com.rishi.drms.entity.Product;
import com.rishi.drms.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid  @RequestBody ProductRequest request) {

        ProductResponse response = productService.createProduct(request);


        ApiResponse<ProductResponse> apiResponse =
                ApiResponse.<ProductResponse>builder()
                        .success(true)
                        .message("Product created successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(){

        List<ProductResponse> products = productService.getAllProducts();

        ApiResponse<List<ProductResponse>> response =
                    ApiResponse.<List<ProductResponse>> builder()
                            .success(true)
                            .message("Product fetched successfully")
                            .data(products)
                            .timestamp(LocalDateTime.now())
                            .build();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public  ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id){

        ProductResponse product = productService.getProductById(id);

        ApiResponse<ProductResponse> response =
                ApiResponse.<ProductResponse>builder()
                        .success(true)
                        .message("Product fetched successfully")
                        .data(product)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        ProductResponse product = productService.updateProduct(id, request);

        ApiResponse<ProductResponse> response =
                ApiResponse.<ProductResponse>builder()
                        .success(true)
                        .message("Product updated successfully")
                        .data(product)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }
}
