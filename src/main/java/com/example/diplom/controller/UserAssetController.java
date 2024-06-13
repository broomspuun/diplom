package com.example.diplom.controller;

import com.example.diplom.dto.UserAssetDTO;
import com.example.diplom.model.User;
import com.example.diplom.model.UserAsset;
import com.example.diplom.service.UserAssetService;
import com.example.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-assets")
public class UserAssetController {

    @Autowired
    private UserAssetService userAssetService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserAsset> getAllUserAssets() {
        return userAssetService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAsset> getUserAssetById(@PathVariable Long id) {
        Optional<UserAsset> userAsset = userAssetService.findById(id);
        return userAsset.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/asset/{assetId}")
    public List<UserAsset> getUserAssetsByAssetId(@PathVariable Long assetId) {
        return userAssetService.findByAssetId(assetId);
    }

    // эндопоинт на добавление нового актива пользователю
    @PostMapping
    public ResponseEntity<UserAsset> createUserAsset(@RequestBody UserAssetRequest userAssetRequest, Authentication authentication) {
        String username = authentication.getName();
        UserAsset newUserAsset = userAssetService.addUserAsset(userAssetRequest, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserAsset);
    }

    // список активов пользователя
    @GetMapping("/user")
    public ResponseEntity<List<UserAssetDTO>> getUserAssets(Authentication authentication) {
        String username = authentication.getName();
        List<UserAssetDTO> userAssets = userAssetService.findByUsername(username);
        return ResponseEntity.ok(userAssets);
    }

    @GetMapping("/user/{assetName}/details")
    public ResponseEntity<UserAssetDTO> getUserAssetDetails(Authentication authentication, @PathVariable String assetName) {
        String username = authentication.getName();
        UserAsset userAsset = userAssetService.findByUsernameAndAssetName(username, assetName);
        UserAssetDTO userAssetDTO = new UserAssetDTO(
                userAsset.getId(),
                userAsset.getAsset().getId(),
                userAsset.getAsset().getName(),
                userAsset.getQuantity(),
                userAsset.getPurchaseDate()
        );
        return ResponseEntity.ok(userAssetDTO);
    }


}
