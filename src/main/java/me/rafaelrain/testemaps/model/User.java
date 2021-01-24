package me.rafaelrain.testemaps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.rafaelrain.testemaps.exception.UserValidationException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table
@ToString
@SuperBuilder
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 64)
    private String name;

    @Column(precision = 2)
    private double balance = 0D;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Asset, Integer> assets = new HashMap<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public int getAssetsCount(Long countId) {
        for (Map.Entry<Asset, Integer> entry : getAssets().entrySet()) {
            Asset asset = entry.getKey();
            if (asset.getId().equals(countId)) {
                return entry.getValue();
            }
        }

        return 0;
    }

    @Data
    @AllArgsConstructor
    public static class Body {

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

}
