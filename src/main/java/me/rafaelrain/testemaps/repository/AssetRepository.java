package me.rafaelrain.testemaps.repository;

import me.rafaelrain.testemaps.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
