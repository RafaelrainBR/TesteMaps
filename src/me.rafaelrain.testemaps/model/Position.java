package me.rafaelrain.testemaps.model;

import java.util.List;
import java.util.stream.Collectors;

public class Position {
    private final String name;
    private final Asset.Type type;
    private final int totalCount;
    private final double totalMarketPrice;
    private final double totalIncome;
    private final double gain;

    public Position(String name, Asset.Type type, int totalCount, double totalMarketPrice, double totalIncome, double gain) {
        this.name = name;
        this.type = type;
        this.totalCount = totalCount;
        this.totalMarketPrice = totalMarketPrice;
        this.totalIncome = totalIncome;
        this.gain = gain;
    }

    public static Position of(Account account, Asset asset) {
        List<Transaction> transactions = account.getTransactions().stream()
                .filter(it -> it.getAsset() != null && it.getAsset().getName().equals(asset.getName()))
                .collect(Collectors.toList());

        int totalCount = 0;
        double totalGain = 0D, totalIncome = 0D;
        if (!transactions.isEmpty()) {
            final List<Transaction> bought = getOfType(transactions, Transaction.Type.INCOME);
            final List<Transaction> sold = getOfType(transactions, Transaction.Type.OUTGOING);

            totalCount = bought.size() - sold.size();

            double averagePrice = bought.stream().mapToDouble(Transaction::getValue).sum() / bought.size();
            totalIncome = asset.getMarketPrice() / averagePrice;

            final double boughtValues = bought.stream().mapToDouble(Transaction::getValue).sum();
            final double soldValues = sold.stream().mapToDouble(Transaction::getValue).sum();

            totalGain = soldValues - boughtValues;
        }

        return new Position(
                asset.getName(),
                asset.getType(),
                totalCount,
                totalCount * asset.getMarketPrice(),
                totalIncome,
                totalGain
        );
    }

    private static List<Transaction> getOfType(List<Transaction> list, Transaction.Type type) {
        return list.stream()
                .filter(it -> it.getType() == type)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public Asset.Type getType() {
        return type;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public double getTotalMarketPrice() {
        return totalMarketPrice;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getGain() {
        return gain;
    }

    @Override
    public String toString() {
        return "Position{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", totalCount=" + totalCount +
                ", totalMarketPrice=" + totalMarketPrice +
                ", totalIncome=" + totalIncome +
                ", gain=" + gain +
                '}';
    }
}
