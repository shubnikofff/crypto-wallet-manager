package com.shubnikofff.crypto_wallet_manager.repository;


import com.shubnikofff.crypto_wallet_manager.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PriceRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Price> prices) {
        final var sql = """
            INSERT INTO price (asset_symbol, usd_value)
            VALUES (:asset_symbol, :usd_value)
            ON CONFLICT(asset_symbol) DO UPDATE SET usd_value = excluded.usd_value
            WHERE price.usd_value IS DISTINCT FROM EXCLUDED.usd_value;
            """;

        final var params = prices.stream()
            .map(price -> Map.of(
                "asset_symbol", price.assetSymbol(),
                "usd_value", price.usdValue()
            ))
            .toArray(Map[]::new);

        jdbcTemplate.batchUpdate(sql, params);
    }



}
