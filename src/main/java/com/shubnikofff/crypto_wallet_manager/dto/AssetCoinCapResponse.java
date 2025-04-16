package com.shubnikofff.crypto_wallet_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record AssetCoinCapResponse(
    @NotBlank String id,
    @NotBlank String symbol,
    @NotNull BigDecimal priceUsd
) {
}
