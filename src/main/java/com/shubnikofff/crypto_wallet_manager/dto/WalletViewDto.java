package com.shubnikofff.crypto_wallet_manager.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record WalletViewDto(
    UUID id,
    BigDecimal total,
    List<AssetViewDto> assets
) {
    public record AssetViewDto(
        String symbol,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal value
    ) {
    }
}
