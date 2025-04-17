package com.shubnikofff.crypto_wallet_manager.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Validated
public record WalletEvaluationRequest(
    @NotNull @NotEmpty List<@Valid EvaluatedAsset> assets
) {
    public record EvaluatedAsset(
        @NotBlank String symbol,
        @NotNull @Positive BigDecimal quantity,
        @NotNull @Positive BigDecimal value
    ) {
    }

}
