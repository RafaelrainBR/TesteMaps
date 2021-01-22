package me.rafaelrain.testemaps.model;

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
    private double balance = 0;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    private Map<Asset, Integer> assets = new HashMap<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Transaction> transactions = new ArrayList<>();

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public void validate() throws UserValidationException {
        if (balance < 0)
            throw new UserValidationException("balance cannot be negative.");
    }
}
