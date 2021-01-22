package me.rafaelrain.testemaps.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    INCOME("Entrada"),
    OUTGOING("Saída");

    private final String description;
}
