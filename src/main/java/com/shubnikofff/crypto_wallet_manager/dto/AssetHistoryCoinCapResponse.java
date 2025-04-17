package com.shubnikofff.crypto_wallet_manager.dto;


import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Instant;

@Validated
public record AssetHistoryCoinCapResponse(
    @NotNull BigDecimal priceUsd,
    @NotNull Instant date
) {
}
