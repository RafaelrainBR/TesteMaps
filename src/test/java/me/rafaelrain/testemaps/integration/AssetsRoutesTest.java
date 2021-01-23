package me.rafaelrain.testemaps.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.rafaelrain.testemaps.enums.AssetType;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.repository.AssetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    private static Date getDateFrom(String text) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(text);
    }

    @Test
    public void findAssets_thenReturnsOk() throws Exception {
        final Asset asset = createNewAsset();
        final List<Asset> assets = assetRepository.findAll();

        mockMvc.perform(get("/assets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(assets)))
                .andExpect(status().isOk());

        assetRepository.deleteById(asset.getId());
    }

    @Test
    public void searchAssets_thenReturnsOk() throws Exception {
        final Asset asset = createNewAsset();

        mockMvc.perform(get("/assets/" + asset.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(asset.getName()))
                .andExpect(jsonPath("$.marketPrice").value(asset.getMarketPrice()));

        assetRepository.deleteById(asset.getId());
    }

    @Test
    public void searchAssets_thenReturnsNotFound() throws Exception {
        mockMvc.perform(get("/assets/64651684"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createAsset_thenReturnsOk() throws Exception {
        final Asset.Body body = getDummyAsset();

        final MvcResult result = mockMvc.perform(post("/assets")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(body.getName()))
                .andExpect(jsonPath("$.marketPrice").value(body.getPrice()))
                .andReturn();

        final JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        final Long id = jsonNode.get("id").asLong();

        assetRepository.deleteById(id);
    }

    @Test
    public void updateAsset_thenReturnsOk() throws Exception {
        final Asset asset = createNewAsset();

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

        assetRepository.deleteById(asset.getId());
    }

    @Test
    public void deleteAsset_thenReturnsOk() throws Exception {
        final Asset asset = createNewAsset();

        mockMvc.perform(delete("/assets/" + asset.getId()))
                .andExpect(status().isOk());

        assertFalse(assetRepository.findById(asset.getId()).isPresent());
    }

    private Asset.Body getDummyAsset() throws Exception {
        return Asset.Body.builder()
                .name("SP-6481RF")
                .price(600D)
                .type(AssetType.RF)
                .emissionDate(getDateFrom("10/06/2021"))
                .expirationDate(getDateFrom("20/06/2021"))
                .build();
    }

    private Asset createNewAsset() throws Exception {
        return assetRepository.save(getDummyAsset().toAsset());
    }
}
