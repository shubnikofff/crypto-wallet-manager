package com.shubnikofff.crypto_wallet_manager.model;

import lombok.With;

import java.math.BigDecimal;
import java.util.UUID;


@With
public record Asset(
    UUID id,
    UUID walletId,
    String capCoinId,
    String symbol,
    BigDecimal quantity,
    BigDecimal price
) {

}
