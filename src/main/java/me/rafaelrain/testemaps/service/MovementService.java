package me.rafaelrain.testemaps.service;

import lombok.RequiredArgsConstructor;
import me.rafaelrain.testemaps.enums.TransactionType;
import me.rafaelrain.testemaps.exception.UserValidationException;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.Transaction;
import me.rafaelrain.testemaps.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final UserService userService;
    private final TransactionService transactionService;

    private static double roundDouble(double value) {
        final BigDecimal bg = new BigDecimal(value).setScale(2, RoundingMode.HALF_DOWN);
        return bg.doubleValue();
    }

    public Transaction buyAssets(User user, Asset asset, int amount, double movementValue, Date date) throws UserValidationException {
        checkDate(asset, date);
        movementValue = roundDouble(movementValue);

        final double newBalance = user.getBalance() - movementValue;
        if (newBalance < 0)
            throw new UserValidationException("Insufficient funds to buy.");

        final Transaction transaction = newTransaction(user, asset, amount, movementValue, date, TransactionType.INCOME);

        if (user.getAssets() == null) user.setAssets(new HashMap<>());
        if (user.getTransactions() == null) user.setTransactions(new ArrayList<>());

        if (user.getAssets().containsKey(asset))
            amount += user.getAssets().get(asset);

        user.getAssets().put(asset, amount);
        user.getTransactions().add(transaction);

        user.setBalance(newBalance);
        transaction.setNewBalance(newBalance);

        userService.save(user);
        return transaction;
    }

    public Transaction sellAssets(User user, Asset asset, int amount, double movementValue, Date date) {
        checkDate(asset, date);
        movementValue = roundDouble(movementValue);

        final Integer assetsCount = user.getAssets().get(asset);
        if (assetsCount == null || assetsCount < amount)
            throw new UserValidationException("Insufficient assets to sell.");

        final Transaction transaction = newTransaction(user, asset, amount, movementValue, date, TransactionType.OUTGOING);

        if (assetsCount == amount) {
            user.getAssets().remove(asset);
        } else {
            user.getAssets().put(asset, assetsCount - amount);
        }

        user.getTransactions().add(transaction);

        double newBalance = user.getBalance() + movementValue;
        user.setBalance(newBalance);
        transaction.setNewBalance(newBalance);

        userService.save(user);
        return transaction;
    }

    private Transaction newTransaction(User user, Asset asset, int amount, double movementValue, Date date, TransactionType type) {
        return transactionService.save(
                Transaction
                        .builder()
                        .user(user)
                        .asset(asset)
                        .date(date)
                        .value(movementValue)
                        .type(type)
                        .description(
                                String.format("%s %d %s for R$%.2f",
                                        type == TransactionType.INCOME ? "Bought" : "Sold",
                                        amount, asset.getName(), movementValue)
                        )
                        .build()
        );
    }

    private void checkDate(Asset asset, Date date) throws UserValidationException {
        if (!(date.after(asset.getEmissionDate()) && date.before(asset.getExpirationDate())))
            throw new UserValidationException("The date is not between asset's emission and expiration date.");
    }
}
