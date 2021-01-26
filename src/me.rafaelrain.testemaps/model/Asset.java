package me.rafaelrain.testemaps.model;

public class Asset {

    private final String name;
    private double marketPrice;
    private Type type;
    private int broughtAssets = 0;
    private int soldAssets = 0;

    public Asset(String name, double marketPrice, Type type) {
        this.name = name;
        this.marketPrice = marketPrice;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getBroughtAssets() {
        return broughtAssets;
    }

    public void setBroughtAssets(int broughtAssets) {
        this.broughtAssets = broughtAssets;
    }

    public int getSoldAssets() {
        return soldAssets;
    }

    public void setSoldAssets(int soldAssets) {
        this.soldAssets = soldAssets;
    }

    public enum Type {
        RF("Renda Fixa"),
        RV("Renda Variável"),
        FUNDO("Fundo");

        private final String description;

        Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
