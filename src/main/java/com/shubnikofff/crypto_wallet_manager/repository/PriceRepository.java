package com.shubnikofff.crypto_wallet_manager.repository;


import com.shubnikofff.crypto_wallet_manager.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PriceRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional
    public int[] saveAll(List<Price> prices) {
        final var sql = """
            INSERT INTO price (asset_symbol, usd_value)
            VALUES (:assetSymbol, :usdValue)
            ON CONFLICT(asset_symbol) DO UPDATE SET usd_value = excluded.usd_value
            WHERE price.usd_value IS DISTINCT FROM EXCLUDED.usd_value;
            """;

        final var params = prices.stream()
            .map(BeanPropertySqlParameterSource::new)
            .toArray(SqlParameterSource[]::new);

        return jdbcTemplate.batchUpdate(sql, params);
    }

}
