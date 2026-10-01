package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;

import java.math.BigDecimal;

public interface ProductPricing {

    BigDecimal calculateSellingPrice(GroceryProduct product);
}
