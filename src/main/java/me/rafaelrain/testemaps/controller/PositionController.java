package me.rafaelrain.testemaps.controller;

import lombok.RequiredArgsConstructor;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.Position;
import me.rafaelrain.testemaps.model.User;
import me.rafaelrain.testemaps.service.AssetService;
import me.rafaelrain.testemaps.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {

    private final UserService userService;
    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<?> getPosition(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "asset_id") Long assetId
    ) {
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

        final Position position = Position.of(user, asset);
        return ResponseEntity.ok(position);
    }
}
