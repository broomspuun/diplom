package com.example.diplom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_assets")
public class UserAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-userAssets")
    private User user;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    @JsonBackReference(value = "asset-userAssets")
    private Asset asset;

    private int quantity;
    private String purchaseDate;
}