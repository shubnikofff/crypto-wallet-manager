package com.shubnikofff.crypto_wallet_manager.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record AddAssetRequest(
    @NotBlank String symbol,
    @NotNull @Positive BigDecimal price,
    @NotNull @Positive BigDecimal quantity
) {
}
