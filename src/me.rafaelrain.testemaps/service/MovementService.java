package me.rafaelrain.testemaps.service;

import me.rafaelrain.testemaps.exception.InsufficientBalanceException;
import me.rafaelrain.testemaps.model.Account;
import me.rafaelrain.testemaps.model.Asset;
import me.rafaelrain.testemaps.model.Transaction;

public class MovementService {

    public void buyAssets(Account account, Asset asset, int amount, double value) throws InsufficientBalanceException {
        double balance = account.getBalance();
        if (balance < value)
            throw new InsufficientBalanceException("Seu saldo é insuficiente para fazer esta compra.");

        final Transaction transaction = new Transaction(value, asset, Transaction.Type.INCOME);

        account.addAssets(asset, amount);
        account.withdrawMoney(value);
        account.getTransactions().add(transaction);
    }

    public void sellAssets(Account account, Asset asset, int amount, double value) throws InsufficientBalanceException {
        if (!account.hasAssets(asset, amount))
            throw new InsufficientBalanceException("Você não tem ativos suficientes para vender.");

        final Transaction transaction = new Transaction(value, asset, Transaction.Type.OUTGOING);

        account.removeAssets(asset, amount);
        account.depositMoney(value);
        account.getTransactions().add(transaction);
    }
}
