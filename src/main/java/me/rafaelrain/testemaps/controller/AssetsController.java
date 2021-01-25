package me.rafaelrain.testemaps.controller;

import lombok.RequiredArgsConstructor;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assets")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AssetsController {
    private final AssetService service;

    @GetMapping
    public ResponseEntity<List<Asset>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> findOne(@PathVariable Long id) {
        Optional<Asset> optional = service.findById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody Asset.Body body) {
        return ResponseEntity.ok(service.save(body.toAsset()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset.Body body) {
        final Optional<Asset> optionalAsset = service.findById(id);
        if (!optionalAsset.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        final Asset asset = optionalAsset.get();
        if (body.getName() != null) asset.setName(body.getName());
        if (body.getPrice() != null) asset.setMarketPrice(body.getPrice());
        if (body.getType() != null) asset.setType(body.getType());
        if (body.getEmissionDate() != null) asset.setEmissionDate(body.getEmissionDate());
        if (body.getExpirationDate() != null) asset.setExpirationDate(body.getExpirationDate());

        return ResponseEntity.ok(service.save(asset));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
