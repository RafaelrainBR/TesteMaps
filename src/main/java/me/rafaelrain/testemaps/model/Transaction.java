package me.rafaelrain.testemaps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.rafaelrain.testemaps.enums.TransactionType;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
@ToString(exclude = {
        "user"
})
@NoArgsConstructor
@SuperBuilder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private double value;
    private double newBalance;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Asset asset;

    @Enumerated
    private TransactionType type;

    @Temporal(TemporalType.DATE)
    private Date date;
}
