package com.example.diplom.repository;


import com.example.diplom.model.User;
import com.example.diplom.model.UserAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserAssetRepository extends JpaRepository<UserAsset, Long> {
    List<UserAsset> findByUserId(Long userId);
    List<UserAsset> findByAssetId(Long assetId);
    List<UserAsset> findByUserUsername(String username);
    UserAsset findByUserUsernameAndAssetName(String username, String assetName);
}