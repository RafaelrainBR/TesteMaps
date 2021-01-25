package me.rafaelrain.testemaps.controller;

import lombok.RequiredArgsConstructor;
import me.rafaelrain.testemaps.enums.MovementType;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.Transaction;
import me.rafaelrain.testemaps.model.User;
import me.rafaelrain.testemaps.service.AssetService;
import me.rafaelrain.testemaps.service.MovementService;
import me.rafaelrain.testemaps.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/movement")
public class MovementController {

    private final UserService userService;
    private final AssetService assetService;

    private final MovementService movementService;

    @GetMapping("/buy")
    public ResponseEntity<?> movementBuy(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "asset_id") Long assetId,
            @RequestParam int amount,
            @RequestParam double value,
            @RequestParam
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date date
    ) {
        return processMovement(userId, assetId, amount, value, date, MovementType.BUY);
    }

    @GetMapping("/sell")
    public ResponseEntity<?> movementSell(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "asset_id") Long assetId,
            @RequestParam(name = "amount") int amount,
            @RequestParam(name = "value") double value,
            @RequestParam
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date date
    ) {
        return processMovement(userId, assetId, amount, value, date, MovementType.SELL);
    }


    private ResponseEntity<?> processMovement(Long userId, Long assetId, int amount, double value, Date date, MovementType type) {
        final Optional<User> optionalUser = userService.findById(userId);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>("user not found with id " + userId, HttpStatus.NOT_FOUND);
        }
        final User user = optionalUser.get();

        final Optional<Asset> optionalAsset = assetService.findById(assetId);
        if (!optionalAsset.isPresent()) {
            return new ResponseEntity<>("asset not found with id " + assetId, HttpStatus.NOT_FOUND);
        }
        final Asset asset = optionalAsset.get();

        Transaction transaction;
        if (type == MovementType.BUY)
            transaction = movementService.buyAssets(user, asset, amount, value, date);
        else
            transaction = movementService.sellAssets(user, asset, amount, value, date);

        return ResponseEntity.ok(transaction);
    }
}
