package me.rafaelrain.testemaps.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.rafaelrain.testemaps.enums.AssetType;
import me.rafaelrain.testemaps.enums.TransactionType;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class Position {
    private final String name;
    private final AssetType type;
    private final int totalCount;
    private final double totalMarketPrice;
    private final double totalIncome;
    private final double gain;

    public static Position of(User user, Asset asset) {
        List<Transaction> transactions = user.getTransactions().stream()
                .filter(it -> it.getAsset() != null && it.getAsset().getName().equals(asset.getName()))
                .collect(Collectors.toList());

        int totalCount = 0;
        double totalGain = 0D, totalIncome = 0D;
        if (!transactions.isEmpty()) {
            val bought = getOfType(transactions, TransactionType.INCOME);
            val sold = getOfType(transactions, TransactionType.OUTGOING);

            totalCount = bought.size() - sold.size();

            double averagePrice = bought.stream().mapToDouble(Transaction::getValue).sum() / bought.size();
            totalIncome = asset.getMarketPrice() / averagePrice;

            val boughtValues = bought.stream().mapToDouble(Transaction::getValue).sum();
            val soldValues = sold.stream().mapToDouble(Transaction::getValue).sum();

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

    private static List<Transaction> getOfType(List<Transaction> list, TransactionType type) {
        return list.stream()
                .filter(it -> it.getType() == type)
                .collect(Collectors.toList());
    }
}