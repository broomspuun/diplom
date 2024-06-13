package com.example.diplom.service;

import com.example.diplom.controller.UserAssetRequest;
import com.example.diplom.dto.UserAssetDTO;
import com.example.diplom.model.Asset;
import com.example.diplom.model.User;
import com.example.diplom.model.UserAsset;
import com.example.diplom.repository.AssetRepository;
import com.example.diplom.repository.UserAssetRepository;
import com.example.diplom.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAssetService {

    @Autowired
    private UserAssetRepository userAssetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssetRepository assetRepository;

    public List<UserAsset> findAll() {
        return userAssetRepository.findAll();
    }

    public Optional<UserAsset> findById(Long id) {
        return userAssetRepository.findById(id);
    }

    // возвращает все активы пользователя
    public List<UserAsset> findByUserId(Long userId) {
        return userAssetRepository.findByUserId(userId);
    }
    // возвращает всех держателей актива
    public List<UserAsset> findByAssetId(Long assetId) {
        return userAssetRepository.findByAssetId(assetId);
    }

    public UserAsset save(UserAsset userAsset) {
        return userAssetRepository.save(userAsset);
    }

    public void deleteById(Long id) {
        userAssetRepository.deleteById(id);
    }

    public UserAsset addUserAsset(UserAssetRequest request, String username) {
        // ищем пользователя которому добавить актив
        User user = userRepository.findByUsername(username);
        // ищем актив
        Asset asset = assetRepository.findById(request.getAssetId()).orElseThrow(() -> new EntityNotFoundException("Asset not found"));

        UserAsset userAsset = new UserAsset();
        userAsset.setUser(user);
        userAsset.setAsset(asset);
        // добавляет количетсво активов
        userAsset.setQuantity(request.getQuantity());
        // дата добавления
        userAsset.setPurchaseDate(LocalDate.now().toString());
        // сохраняем
        return userAssetRepository.save(userAsset);
    }
    public List<UserAssetDTO> findByUsername(String username) {
        List<UserAsset> userAssets = userAssetRepository.findByUserUsername(username);
        return userAssets.stream()
                .map(userAsset -> new UserAssetDTO(
                        userAsset.getId(),
                        userAsset.getAsset().getId(),
                        userAsset.getAsset().getName(),
                        userAsset.getQuantity(),
                        userAsset.getPurchaseDate()
                ))
                .collect(Collectors.toList());
    }

    public UserAsset findByUsernameAndAssetName(String username, String assetName) {
        return userAssetRepository.findByUserUsernameAndAssetName(username, assetName);
    }

}
