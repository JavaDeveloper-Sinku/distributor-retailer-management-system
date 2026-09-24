package com.rishi.drms.service;

import com.rishi.drms.dto.request.InventoryRequest;
import com.rishi.drms.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    List<InventoryResponse> getAllInventory();

    InventoryResponse getInventoryById(Long id);

    InventoryResponse getInventoryByProductId(Long productId);

    InventoryResponse updateInventory(Long id, InventoryRequest request);

    void deleteInventory(Long id);
}