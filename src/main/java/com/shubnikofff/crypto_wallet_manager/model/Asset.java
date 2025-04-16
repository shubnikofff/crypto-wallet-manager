package com.shubnikofff.crypto_wallet_manager.model;

import java.math.BigDecimal;
import java.util.UUID;

public record Asset(
    UUID id,
    UUID walletId,
    String capCoinId,
    String symbol,
    BigDecimal quantity,
    BigDecimal price
) {
    public BigDecimal value() {
        return quantity.multiply(price);
    }
}
