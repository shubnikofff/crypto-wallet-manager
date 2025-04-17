package com.shubnikofff.crypto_wallet_manager.exception;

public class UniqueConstraintException extends RuntimeException {

    public UniqueConstraintException(String message) {
        super(message);
    }
}
