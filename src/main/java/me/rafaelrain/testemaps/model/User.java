package me.rafaelrain.testemaps.model;

import lombok.Data;
import me.rafaelrain.testemaps.exception.UserValidationException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String name;

    @Column(precision = 2)
    private double balance = 0;

    @OneToMany(mappedBy = "owner")
    private List<Asset> assets = new ArrayList<>();

    @OneToMany(mappedBy = "user")
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
