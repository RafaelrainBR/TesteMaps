package me.rafaelrain.testemaps.model;

import me.rafaelrain.testemaps.exception.InsufficientBalanceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {

    private final Map<Asset, Integer> assets = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private double balance;

    public Account() {
        this(0);
    }

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public void depositMoney(double valor) throws IllegalArgumentException {
        assert valor > 0;
        balance += valor;
    }

    public void withdrawMoney(double valor) throws IllegalArgumentException, InsufficientBalanceException {
        assert valor > 0;
        if (balance < valor) throw new InsufficientBalanceException("Saldo insuficiente para fazer esse saque.");
        balance -= valor;
    }

    public boolean hasAssets(Asset asset, int amount) {
        final Integer count = assets.get(asset);

        return count != null && count >= amount;
    }

    public void addAssets(Asset asset, int amount) {
        assets.merge(asset, amount, Integer::sum);
    }

    public void removeAssets(Asset asset, int amount) {
        final Integer count = assets.get(asset);
        if (count == amount)
            assets.remove(asset);
        else
            assets.put(asset, count - amount);
    }

    public double getBalance() {
        return balance;
    }

    public Map<Asset, Integer> getAssets() {
        return assets;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", assets=" + assets +
                ", transactions=" + transactions +
                '}';
    }
}
