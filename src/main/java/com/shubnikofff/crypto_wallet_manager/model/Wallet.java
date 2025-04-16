package com.shubnikofff.crypto_wallet_manager.model;

import java.util.UUID;

public record Wallet(
    UUID id,
    String ownerEmail
) {
}
