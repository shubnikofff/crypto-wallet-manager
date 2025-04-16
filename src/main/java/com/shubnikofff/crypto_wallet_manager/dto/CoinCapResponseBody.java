package com.shubnikofff.crypto_wallet_manager.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record CoinCapResponseBody<T>(@NotNull List<T> data) {
}
