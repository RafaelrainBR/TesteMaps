package me.rafaelrain.testemaps.repository;

import me.rafaelrain.testemaps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
