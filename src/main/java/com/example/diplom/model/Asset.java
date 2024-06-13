package com.example.diplom.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Double price;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "asset-userAssets")
    private List<UserAsset> userAssets;
}