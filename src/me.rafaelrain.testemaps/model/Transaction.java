package me.rafaelrain.testemaps.model;

public class Transaction {

    private final double value;
    private final Asset asset;
    private final Transaction.Type type;

    public Transaction(double value, Asset asset, Transaction.Type type) {
        this.value = value;
        this.asset = asset;
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public Asset getAsset() {
        return asset;
    }

    public Transaction.Type getType() {
        return type;
    }

    public enum Type {
        INCOME,
        OUTGOING
    }
}
