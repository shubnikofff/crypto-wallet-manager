package com.shubnikofff.crypto_wallet_manager.model;

import java.math.BigDecimal;

public record Price(
    String assetSymbol,
    BigDecimal usdValue
) {
}
