package me.rafaelrain.testemaps.model;

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
    @GeneratedValue
    private Long id;

    private String description;
    private double value;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne
    private Asset asset;

    @Enumerated
    private TransactionType type;

    @Temporal(TemporalType.DATE)
    private Date date;
}
