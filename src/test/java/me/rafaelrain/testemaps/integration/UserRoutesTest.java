package me.rafaelrain.testemaps.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.rafaelrain.testemaps.TesteMapsApplication;
import me.rafaelrain.testemaps.config.JpaConfig;
import me.rafaelrain.testemaps.model.User;
import me.rafaelrain.testemaps.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;

import static me.rafaelrain.testemaps.util.TestUtil.createNewUser;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {
        TesteMapsApplication.class,
        JpaConfig.class})
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserRoutesTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUsers_thenReturnsOk() throws Exception {
        final List<User> users = userRepository.findAll();

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(users)))
                .andExpect(status().isOk());
    }

    @Test
    public void searchUser_thenReturnsOk() throws Exception {
        final User user = createNewUser(userRepository);

        mockMvc.perform(get("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.balance").value(user.getBalance()));
    }

    @Test
    public void searchUser_thenReturnsNotFound() throws Exception {
        mockMvc.perform(get("/users/49846465"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createUser_thenReturnsOk() throws Exception {
        final User.Body body = new User.Body("user only for tests", 6546D);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(body.getName()))
                .andExpect(jsonPath("$.balance").value(body.getBalance()));
    }

    @Test
    public void updateUser_thenReturnsOk() throws Exception {
        final User user = createNewUser(userRepository);

        final String newName = "newname";
        final double newBalance = 489198;

        final User.Body body = new User.Body(newName, newBalance);

        mockMvc.perform(put("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.balance").value(newBalance));
    }

    @Test
    public void deleteUser_thenReturnsOk() throws Exception {
        final User user = createNewUser(userRepository);

        mockMvc.perform(delete("/users/" + user.getId()))
                .andExpect(status().isOk());

        assertFalse(userRepository.findById(user.getId()).isPresent());
    }
}
