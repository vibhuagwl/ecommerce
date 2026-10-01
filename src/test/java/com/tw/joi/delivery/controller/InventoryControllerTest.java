package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.dto.InventoryHealthResponse;
import com.tw.joi.delivery.dto.StockStatus;
import com.tw.joi.delivery.dto.response.ProductHealth;
import com.tw.joi.delivery.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;


    @Test
    void shouldReturnTheHealthOfTheStore() throws Exception {
        Mockito.when(inventoryService.healthResponse("store101"))
                .thenReturn(new InventoryHealthResponse("store101",
                        "Fresh Picks", 1, 1,
                        List.of(new ProductHealth("product101", "Wheat Bread", 30, 10, StockStatus.HEALTHY),
                                new ProductHealth("product102", "Spinach", 10, 10, StockStatus.LOW_STOCK))));
        String getUrl = "/inventory/health?storeId={storeId}";
        //add required mocking.
        mockMvc.perform(MockMvcRequestBuilders.get(getUrl, "store101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").value("store101"))
                .andExpect(jsonPath("$.storeName").value("Fresh Picks"))
                .andExpect(jsonPath("$.healthyCount").value(1))
                .andExpect(jsonPath("$.lowStockCount").value(1))
                .andExpect(jsonPath("$.products[0].productName").value("Wheat Bread"));
        Mockito.verify(inventoryService).healthResponse("store101");
        //put meaning assertions

    }
}