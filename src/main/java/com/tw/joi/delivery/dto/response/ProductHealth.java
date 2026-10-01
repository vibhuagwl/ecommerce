package com.tw.joi.delivery.dto.response;

import com.tw.joi.delivery.dto.StockStatus;

public record ProductHealth(
        String productId, String productName, long availableStock, long lowStockThreshold, StockStatus status) {


}
