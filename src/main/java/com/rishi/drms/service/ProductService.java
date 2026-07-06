package com.rishi.drms.service;

import com.rishi.drms.dto.ProductRequest;
import com.rishi.drms.dto.ProductResponse;
import com.rishi.drms.entity.Product;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest product);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);
}
