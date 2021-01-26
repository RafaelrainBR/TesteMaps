package me.rafaelrain.testemaps.exception;

public class InsufficientBalanceException extends IllegalStateException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
