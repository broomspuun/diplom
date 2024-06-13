package com.example.diplom.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class DataInitializer {

    @Autowired
    private StockDataService stockDataService;

    @PostConstruct
    public void init() {
        try {
            stockDataService.refreshStockData();
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to fetch and refresh stock data");
            e.printStackTrace();
        }
    }
}
