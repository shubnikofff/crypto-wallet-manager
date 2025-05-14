package com.shubnikofff.crypto_wallet_manager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("wallet")
public record Wallet(
	@Id
    UUID id,
    String ownerEmail
) {
}
