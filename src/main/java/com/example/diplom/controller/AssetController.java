package com.example.diplom.controller;

import com.example.diplom.model.Asset;
import com.example.diplom.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    // эндпоинт для получения списка всех активов
    @GetMapping
    public List<Asset> getAllAssets() {
        return assetService.findAll();
    }

    // эндпоинт на создание нового актива
    @PostMapping
    public Asset createAsset(@RequestBody Asset asset) {
        return assetService.save(asset);
    }

    @GetMapping("/{instId}/details")
    public ResponseEntity<Map<String, Object>> getAssetDetails(@PathVariable String instId) {
        Map<String, Object> details = assetService.getAssetDetails(instId);
        return details != null ? ResponseEntity.ok(details) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) {
        Optional<Asset> asset = assetService.findById(assetId);
        return asset.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}