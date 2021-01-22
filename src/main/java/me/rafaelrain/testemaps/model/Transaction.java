package me.rafaelrain.testemaps.model;

import lombok.Data;
import me.rafaelrain.testemaps.enums.TransactionType;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Transaction {
    //TODO: Controller e validation

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(optional = false)
    @JoinColumn
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn
    private Asset asset;

    @Enumerated
    private TransactionType type;

    @Temporal(TemporalType.DATE)
    private Date date;
}
