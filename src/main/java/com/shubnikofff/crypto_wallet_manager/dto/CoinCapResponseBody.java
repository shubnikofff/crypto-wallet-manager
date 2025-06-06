package com.shubnikofff.crypto_wallet_manager.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record CoinCapResponseBody<T>(@NotNull T data) {
}
