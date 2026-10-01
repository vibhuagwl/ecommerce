package com.tw.joi.delivery.service;

import com.tw.joi.delivery.dto.InventoryHealthResponse;
import com.tw.joi.delivery.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryServiceTest {

    private final InventoryService inventoryService = new InventoryService(
            new StoreService(),
            new ProductService());

    @Test
    void returnsHealthForSeededStore() {
        InventoryHealthResponse response = inventoryService.healthResponse("store101");

        assertEquals("store101", response.storeId());
        assertEquals("Fresh Picks", response.storeName());
        assertEquals(3, response.healthyCount());
        assertEquals(0, response.lowStockCount());
        assertEquals(3, response.products().size());
    }

    @Test
    void returnsNotFoundForUnknownStore() {
        assertThrows(NotFoundException.class, () -> inventoryService.healthResponse("missing-store"));
    }
}
