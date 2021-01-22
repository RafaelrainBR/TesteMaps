package me.rafaelrain.testemaps.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.rafaelrain.testemaps.enums.AssetType;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
@ToString
@SuperBuilder
@NoArgsConstructor
public class Asset {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 64)
    private String name;

    @Column(precision = 8)
    private double marketPrice;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private AssetType type;

    @Temporal(TemporalType.DATE)
    private Date emissionDate;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;
}
