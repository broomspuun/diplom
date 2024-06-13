package com.example.diplom.dto;

import lombok.Data;

@Data
public class UserAssetDTO {
    private Long id;
    private Long assetId;
    private String assetName;
    private int quantity;
    private String purchaseDate;

    public UserAssetDTO(Long id, Long assetId, String assetName, int quantity, String purchaseDate) {
        this.id = id;
        this.assetId = assetId;
        this.assetName = assetName;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }
}