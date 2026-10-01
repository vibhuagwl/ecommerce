package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.dto.InventoryHealthResponse;
import com.tw.joi.delivery.dto.StockStatus;
import com.tw.joi.delivery.dto.response.ProductHealth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StoreService storeService;
    private final ProductService productService;

    public InventoryHealthResponse healthResponse(String storeId) {
        GroceryStore outlet = storeService.fetchStoreById(storeId);
        List<GroceryProduct> products = productService.getProductForOutlet(outlet.getOutletId());
        List<ProductHealth> productHealths = products.stream()
                .map(this::toProductHealth)
                .toList();

        int lowStockCount = (int) products.stream()
                .filter(GroceryProduct::isLowStock)
                .count();
        return new InventoryHealthResponse(outlet.getOutletId(),
                outlet.getName(),
                productHealths.size() - lowStockCount,
                lowStockCount,
                productHealths);
    }

    private ProductHealth toProductHealth(GroceryProduct product) {
        StockStatus status = product.isLowStock() ? StockStatus.LOW_STOCK : StockStatus.HEALTHY;
        return new ProductHealth(product.getProductId(),
                product.getProductName(),
                product.getAvailableStock(),
                product.getThreshold(),
                status);
    }
}
