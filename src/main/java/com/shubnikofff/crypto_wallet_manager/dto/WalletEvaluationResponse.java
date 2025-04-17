package com.shubnikofff.crypto_wallet_manager.dto;

import java.math.BigDecimal;

public record WalletEvaluationResponse(
    BigDecimal total,
    String bestAsset,
    BigDecimal bestPerformance,
    String worstAsset,
    BigDecimal worstPerformance
) {
}
