package me.rafaelrain.testemaps.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.Position;
import me.rafaelrain.testemaps.model.User;
import me.rafaelrain.testemaps.repository.AssetRepository;
import me.rafaelrain.testemaps.repository.UserRepository;
import me.rafaelrain.testemaps.service.MovementService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static me.rafaelrain.testemaps.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PositionRoutesTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MovementService movementService;

    @Test
    public void consultPosition_andReturnOk() throws Exception {
        User user = createNewUser(userRepository);
        final Asset asset = createNewAsset(assetRepository);

        movementService.buyAssets(user, asset, 18, 235.132D, getDateFrom("15-06-2021"));

        final MvcResult mvcResult = mockMvc.perform(get("/position")
                .queryParam("user_id", String.valueOf(user.getId()))
                .queryParam("asset_id", String.valueOf(asset.getId())))
                .andExpect(status().isOk())
                .andReturn();

        user = userRepository.findById(user.getId()).get();
        final Position position = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Position.class);

        assertEquals(Position.of(user, asset), position);
    }

    @AfterEach
    public void deleteEach() {
        userRepository.deleteById(1L);
        assetRepository.deleteById(1L);
    }

}
