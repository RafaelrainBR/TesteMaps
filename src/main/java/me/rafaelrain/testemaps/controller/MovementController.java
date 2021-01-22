package me.rafaelrain.testemaps.controller;

import lombok.RequiredArgsConstructor;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.User;
import me.rafaelrain.testemaps.service.AssetService;
import me.rafaelrain.testemaps.service.MovementService;
import me.rafaelrain.testemaps.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movement")
public class MovementController {

    private final UserService userService;
    private final AssetService assetService;

    private final MovementService movementService;

    @GetMapping("/buy")
    public ResponseEntity<?> movementBuy(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "asset_id") Long assetId,
            @RequestParam(name = "amount") int amount,
            @RequestParam(name = "value") double value,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date
    ) {
        final Optional<User> optionalUser = userService.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        final User user = optionalUser.get();

        final Optional<Asset> optionalAsset = assetService.findById(assetId);
        if (!optionalAsset.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        final Asset asset = optionalAsset.get();

        movementService.buyAssets(user, asset, amount, value, date);

        return ResponseEntity.ok(user);
    }
}
