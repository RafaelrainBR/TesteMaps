package me.rafaelrain.testemaps.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
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
    private Double balance = 0D;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Asset, Integer> assets = new HashMap<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}
