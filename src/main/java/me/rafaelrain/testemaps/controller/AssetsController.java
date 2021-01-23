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
    public ResponseEntity<?> createNew(@RequestBody Asset asset) {
        return ResponseEntity.ok(service.save(asset));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

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
