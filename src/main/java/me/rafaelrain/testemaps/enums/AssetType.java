package me.rafaelrain.testemaps.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetType {
    RV("Renda fixa"),
    RF("Renda variável"),
    FUND("Fundo");

    private final String description;
}
