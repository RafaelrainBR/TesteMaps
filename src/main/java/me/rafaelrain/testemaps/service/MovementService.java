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
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final UserService userService;

    private static double roundDouble(double value) {
        final BigDecimal bg = new BigDecimal(value).setScale(2, RoundingMode.HALF_DOWN);
        return bg.doubleValue();
    }

    public void buyAssets(User user, Asset asset, int amount, double movementValue, Date date) throws UserValidationException {
        checkDate(asset, date);
        movementValue = roundDouble(movementValue);

        final double newBalance = user.getBalance() - movementValue;
        if (newBalance < 0)
            throw new UserValidationException("Insufficient funds to buy.");

        final Transaction transaction = newTransaction(user, asset, amount, movementValue, date, TransactionType.OUTGOING);

//        if(user.getAssets() == null) user.setAssets(new HashMap<>());
//        if(user.getTransactions() == null) user.setTransactions(new ArrayList<>());

        // TODO: Resolver bug de deixar as collections do User como null.
        if (user.getAssets().containsKey(asset))
            amount += user.getAssets().get(asset);

        user.getAssets().put(asset, amount);
        user.getTransactions().add(transaction);
        user.setBalance(newBalance);

        userService.save(user);
    }

    public void sellAssets(User user, Asset asset, int amount, double movementValue, Date date) {
        checkDate(asset, date);
        movementValue = roundDouble(movementValue);

        final Integer assetsCount = user.getAssets().get(asset);
        if (assetsCount == null || assetsCount < amount)
            throw new UserValidationException("Insufficient assets to sell.");

        final Transaction transaction = newTransaction(user, asset, amount, movementValue, date, TransactionType.INCOME);

        if (assetsCount == amount) {
            user.getAssets().remove(asset);
        } else {
            user.getAssets().put(asset, assetsCount - amount);
        }

        user.getTransactions().add(transaction);
        user.setBalance(user.getBalance() + movementValue);

        userService.save(user);
    }

    private Transaction newTransaction(User user, Asset asset, int amount, double movementValue, Date date, TransactionType type) {
        return Transaction
                .builder()
                .user(user)
                .asset(asset)
                .date(date)
                .value(movementValue)
                .type(type)
                .description(
                        String.format("%s %d %s for R$%.2f",
                                type == TransactionType.INCOME ? "Sold" : "Bought",
                                amount, asset.getName(), movementValue)
                )
                .build();
    }

    private void checkDate(Asset asset, Date date) throws UserValidationException {
        if (!(date.after(asset.getEmissionDate()) && date.before(asset.getExpirationDate())))
            throw new UserValidationException("The date is not between asset's emission and expiration date.");
    }
}
