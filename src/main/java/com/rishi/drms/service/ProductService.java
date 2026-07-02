package com.rishi.drms.service;

import com.rishi.drms.dto.ProductRequest;
import com.rishi.drms.dto.ProductResponse;
import com.rishi.drms.entity.Product;

public interface ProductService {

    ProductResponse createProduct(ProductRequest product);
}
