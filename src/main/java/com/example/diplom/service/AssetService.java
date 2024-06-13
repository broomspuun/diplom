package com.example.diplom.service;

import com.example.diplom.model.Asset;
import com.example.diplom.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class AssetService {

    private static final String API_URL = "https://www.okx.com/api/v5/market/tickers?instType=SPOT";
    private static final String DETAILED_API_URL = "https://www.okx.com/api/v5/market/ticker?instId=";

    private final RestTemplate restTemplate;

    public AssetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void fetchAndSaveAssets() {
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
        // existing implementation
    }

    public Map<String, Object> getAssetDetails(String instId) {
        String url = DETAILED_API_URL + instId;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && response.containsKey("data")) {
            return ((List<Map<String, Object>>) response.get("data")).get(0);
        }
        return null;
    }

    @Autowired
    private AssetRepository assetRepository;

    // возвращает все активы
    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    // ищет актив по его id
    public Optional<Asset> findById(Long id) {
        return assetRepository.findById(id);
    }

    // сохраняет новый актив
    public Asset save(Asset asset) {
        return assetRepository.save(asset);
    }

    // удаляет актив по id
    public void deleteById(Long id) {
        assetRepository.deleteById(id);
    }
}