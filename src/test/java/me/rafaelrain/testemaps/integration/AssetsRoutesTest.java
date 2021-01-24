package me.rafaelrain.testemaps.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.repository.AssetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static me.rafaelrain.testemaps.util.TestUtil.createNewAsset;
import static me.rafaelrain.testemaps.util.TestUtil.getDummyAsset;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AssetsRoutesTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AssetRepository assetRepository;

    @Test
    public void findAssets_thenReturnsOk() throws Exception {
        final List<Asset> assets = assetRepository.findAll();

        mockMvc.perform(get("/assets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(assets)))
                .andExpect(status().isOk());
    }

    @Test
    public void searchAssets_thenReturnsOk() throws Exception {
        final Asset asset = createNewAsset(assetRepository);

        mockMvc.perform(get("/assets/" + asset.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(asset.getName()))
                .andExpect(jsonPath("$.marketPrice").value(asset.getMarketPrice()));
    }

    @Test
    public void searchAssets_thenReturnsNotFound() throws Exception {
        mockMvc.perform(get("/assets/64651684"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createAsset_thenReturnsOk() throws Exception {
        final Asset.Body body = getDummyAsset();

        mockMvc.perform(post("/assets")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(body.getName()))
                .andExpect(jsonPath("$.marketPrice").value(body.getPrice()));
    }

    @Test
    public void updateAsset_thenReturnsOk() throws Exception {
        final Asset asset = createNewAsset(assetRepository);

        final double newPrice = 436.152425;

        final Asset.Body body = Asset.Body.builder()
                .price(newPrice)
                .build();

        mockMvc.perform(put("/assets/" + asset.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(asset.getName()))
                .andExpect(jsonPath("$.marketPrice").value(newPrice));
    }

    @Test
    public void deleteAsset_thenReturnsOk() throws Exception {
        final Asset asset = createNewAsset(assetRepository);

        mockMvc.perform(delete("/assets/" + asset.getId()))
                .andExpect(status().isOk());

        assertFalse(assetRepository.findById(asset.getId()).isPresent());
    }

    @AfterEach
    public void deleteEach() {
        assetRepository.deleteById(26L);
    }
}
