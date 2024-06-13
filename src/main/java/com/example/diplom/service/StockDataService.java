package com.example.diplom.service;

import com.example.diplom.model.Asset;
import com.example.diplom.repository.AssetRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.List;

@Service
public class StockDataService {

    @Autowired
    private AssetRepository assetRepository;

    private static final String API_URL = "https://www.okx.com/api/v5/market/tickers?instType=SPOT";

    public void refreshStockData() throws IOException, InterruptedException {
        // Удаление старых данных
        assetRepository.deleteAll();

        // Загрузка новых данных
        fetchAndSaveStockData();
    }

    private void fetchAndSaveStockData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response.body());

        // Проверка наличия данных
        if (jsonResponse.has("data")) {
            JsonNode data = jsonResponse.get("data");
            for (JsonNode assetData : data) {
                String name = assetData.get("instId").asText();
                double price = assetData.get("last").asDouble();

                Asset asset = new Asset();
                asset.setName(name);
                asset.setType("crypto");
                asset.setPrice(price);
                assetRepository.save(asset);
            }
        } else {
            System.err.println("No data found from OKX API");
        }
    }
}
