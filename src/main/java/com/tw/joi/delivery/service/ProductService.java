package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.seedData.SeedData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final List<GroceryProduct> products = SeedData.groceryProducts;

    public GroceryProduct getProduct(String productId, String outletId) {
        return products.stream()
                .filter(groceryProduct -> groceryProduct.getProductId()
                        .equals(productId) && groceryProduct.getStore()
                        .getOutletId()
                        .equals(outletId))
                .findFirst()
                .orElse(null);
    }

    public List<GroceryProduct> getProductForOutlet(String outletId) {
        return products.stream()
                .filter(products -> products.getStore()
                        .getOutletId()
                        .equals(outletId))
                .toList();
    }


}
