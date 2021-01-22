package me.rafaelrain.testemaps.repository;

import me.rafaelrain.testemaps.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
}
