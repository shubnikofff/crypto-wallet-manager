package com.shubnikofff.crypto_wallet_manager.repository;


import com.shubnikofff.crypto_wallet_manager.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WalletRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean exists(String ownerEmail) {
        final var result = namedParameterJdbcTemplate.queryForObject(
            "SELECT EXISTS (SELECT 1 FROM wallet WHERE owner_email = :owner_email)",
            Map.of("owner_email", ownerEmail),
            Boolean.class
        );

        return Boolean.TRUE.equals(result);
    }

    public boolean exists(UUID id) {
        final var result = namedParameterJdbcTemplate.queryForObject(
            "SELECT EXISTS (SELECT 1 FROM wallet WHERE id = :id)",
            Map.of("id", id),
            Boolean.class
        );

        return Boolean.TRUE.equals(result);
    }

    public void save(Wallet wallet) {
        namedParameterJdbcTemplate.update(
            "INSERT INTO wallet(id, owner_email) VALUES (:id, :owner_email)",
            Map.of(
                "id", wallet.id(),
                "owner_email", wallet.ownerEmail()
            )
        );
    }
}
