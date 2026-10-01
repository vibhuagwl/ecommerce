package com.tw.joi.delivery.dto;

import com.tw.joi.delivery.dto.response.ProductHealth;

import java.util.List;

public record InventoryHealthResponse(
        String storeId,
        String storeName,
        int healthyCount,
        int lowStockCount,
        List<ProductHealth> products
) {

}
