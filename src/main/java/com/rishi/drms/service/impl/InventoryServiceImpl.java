package com.rishi.drms.service.impl;

import com.rishi.drms.dto.request.InventoryRequest;
import com.rishi.drms.dto.response.InventoryResponse;
import com.rishi.drms.entity.Inventory;
import com.rishi.drms.exception.ResourceAlreadyExistsException;
import com.rishi.drms.exception.ResourceNotFoundException;
import com.rishi.drms.mapper.InventoryMapper;
import com.rishi.drms.repository.InventoryRepository;
import com.rishi.drms.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public InventoryResponse createInventory(InventoryRequest request) {

        if (inventoryRepository.existsByProductId(request.getProductId())) {
            throw new ResourceAlreadyExistsException(
                    "Inventory already exists for product id: "
                            + request.getProductId()
            );
        }

        Inventory inventory = InventoryMapper.toEntity(request);

        Inventory savedInventory = inventoryRepository.save(inventory);

        return InventoryMapper.toResponse(savedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .map(InventoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryById(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found with id: " + id
                        )
                );

        return InventoryMapper.toResponse(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryByProductId(Long productId) {

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found for product id: "
                                        + productId
                        )
                );

        return InventoryMapper.toResponse(inventory);
    }

    @Override
    @Transactional
    public InventoryResponse updateInventory(
            Long id,
            InventoryRequest request) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found with id: " + id
                        )
                );

        if (!inventory.getProductId().equals(request.getProductId())
                && inventoryRepository.existsByProductId(
                request.getProductId())) {

            throw new ResourceAlreadyExistsException(
                    "Inventory already exists for product id: "
                            + request.getProductId()
            );
        }

        inventory.setProductId(request.getProductId());
        inventory.setAvailableStock(request.getAvailableStock());
        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory updatedInventory =
                inventoryRepository.save(inventory);

        return InventoryMapper.toResponse(updatedInventory);
    }

    @Override
    @Transactional
    public void deleteInventory(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found with id: " + id
                        )
                );

        inventoryRepository.delete(inventory);
    }
}