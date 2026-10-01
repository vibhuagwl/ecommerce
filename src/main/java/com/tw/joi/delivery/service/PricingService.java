package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PricingService implements ProductPricing {

    private final List<PricingStrategy> strategies;

    public PricingService(List<PricingStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
    }

    @Override
    public BigDecimal calculateSellingPrice(GroceryProduct product) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(product))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No pricing strategy found for product: " + product.getProductId()))
                .calculatePrice(product);
    }
}
