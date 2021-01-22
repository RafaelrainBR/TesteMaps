package me.rafaelrain.testemaps.service;

import lombok.RequiredArgsConstructor;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository repository;

    public List<Asset> findAll() {
        return repository.findAll();
    }

    public Optional<Asset> findById(Long id) {
        return repository.findById(id);
    }

    public Asset save(Asset asset) {
        return repository.save(asset);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
