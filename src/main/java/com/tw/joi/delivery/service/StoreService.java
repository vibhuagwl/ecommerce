package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryStore;
import com.tw.joi.delivery.exception.NotFoundException;
import com.tw.joi.delivery.seedData.SeedData;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    public GroceryStore fetchStoreById(String storeId) {
        return SeedData.stores.stream()
                .filter(outlet -> outlet.getOutletId().equals(storeId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Store not found: " + storeId));
    }
}
