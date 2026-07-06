package com.rishi.drms.service.impl;

import com.rishi.drms.dto.ProductRequest;
import com.rishi.drms.dto.ProductResponse;
import com.rishi.drms.entity.Product;
import com.rishi.drms.exception.ResourceNotFoundException;
import com.rishi.drms.mapper.ProductMapper;
import com.rishi.drms.repository.ProductRepository;
import com.rishi.drms.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        if (productRepository.existsBySku(request.getSku())){
            throw new RuntimeException("Product with sku already exists");
        }

        Product product = ProductMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));


        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    product.setName(request.getName());
    product.setSku(request.getSku());
    product.setDescription(request.getDescription());
    product.setPrice(request.getPrice());
    product.setPrice(request.getPrice());
    product.setStockQuantity(request.getStockQuantity());

    Product updatedProduct = productRepository.save(product);

    return ProductMapper.toResponse(updatedProduct);
    }
}
