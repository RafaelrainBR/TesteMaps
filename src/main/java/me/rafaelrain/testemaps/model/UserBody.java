package me.rafaelrain.testemaps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.rafaelrain.testemaps.exception.UserValidationException;

@Data
@AllArgsConstructor
public class UserBody {

    private String name;
    private Double balance;

    public void validate() throws UserValidationException {
        if (balance < 0)
            throw new UserValidationException("balance cannot be negative.");
    }

    public User toUser() {
        return User.builder()
                .name(name)
                .balance(balance)
                .build();
    }
}
