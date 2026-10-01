package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;

public interface StockReservation {

    boolean reserveOneUnit(GroceryProduct product);
}
