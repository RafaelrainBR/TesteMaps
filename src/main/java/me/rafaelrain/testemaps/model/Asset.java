package me.rafaelrain.testemaps.model;

import lombok.Data;
import me.rafaelrain.testemaps.enums.AssetType;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String name;

    @Column(precision = 8)
    private double marketPrice;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private AssetType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User owner;

    @Temporal(TemporalType.DATE)
    private Date emissionDate;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;
}
