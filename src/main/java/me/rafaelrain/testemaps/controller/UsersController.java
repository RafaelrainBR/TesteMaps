package me.rafaelrain.testemaps.controller;

import lombok.RequiredArgsConstructor;
import me.rafaelrain.testemaps.model.User;
import me.rafaelrain.testemaps.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        Optional<User> optional = service.findById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createNew(@RequestBody User.Body body) {
        return ResponseEntity.ok(service.save(body.toUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User.Body body) {
        final Optional<User> optionalUser = service.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        body.validate();

        final User user = optionalUser.get();
        if (body.getName() != null) user.setName(body.getName());
        if (body.getBalance() != null) user.setBalance(body.getBalance());

        return ResponseEntity.ok(service.save(user));
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
