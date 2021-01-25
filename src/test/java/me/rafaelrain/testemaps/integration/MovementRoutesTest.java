package me.rafaelrain.testemaps.integration;

import me.rafaelrain.testemaps.TesteMapsApplication;
import me.rafaelrain.testemaps.config.JpaConfig;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.User;
import me.rafaelrain.testemaps.repository.AssetRepository;
import me.rafaelrain.testemaps.repository.UserRepository;
import me.rafaelrain.testemaps.service.MovementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static me.rafaelrain.testemaps.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {
        TesteMapsApplication.class,
        JpaConfig.class})
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MovementRoutesTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MovementService movementService;

    @Test
    public void buyAssets_thenReturnsOk() throws Exception {
        final User user = createNewUser(userRepository);
        final Asset asset = createNewAsset(assetRepository);

        mockMvc.perform(get("/movement/buy")
                .queryParam("user_id", String.valueOf(user.getId()))
                .queryParam("asset_id", String.valueOf(asset.getId()))
                .queryParam("amount", String.valueOf(18))
                .queryParam("value", String.valueOf(235.132D))
                .queryParam("date", "15-06-2021"))
                .andExpect(status().isOk());
    }

    @Test
    public void sellAssets_thenReturnsOk() throws Exception {
        User user = createNewUser(userRepository);
        final Asset asset = createNewAsset(assetRepository);

        movementService.buyAssets(user, asset, 18, 235.132D, getDateFrom("15-06-2021"));

        mockMvc.perform(get("/movement/sell")
                .queryParam("user_id", String.valueOf(user.getId()))
                .queryParam("asset_id", String.valueOf(asset.getId()))
                .queryParam("amount", String.valueOf(17))
                .queryParam("value", String.valueOf(235.132D))
                .queryParam("date", "15-06-2021"))
                .andExpect(status().isOk());

        user = userRepository.findById(user.getId()).get();
        int assets = user.getAssetsCount(asset.getId());
        assertEquals(1, assets);
    }
}
