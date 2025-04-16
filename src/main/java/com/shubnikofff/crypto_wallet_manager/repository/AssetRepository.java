package com.shubnikofff.crypto_wallet_manager.repository;


import com.shubnikofff.crypto_wallet_manager.model.Asset;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AssetRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final JdbcTemplate jdbcTemplate;

	public List<String> findUniqueCoinCapIds() {
		return jdbcTemplate.queryForList("SELECT DISTINCT coin_cap_id FROM asset", String.class);
	}

	public List<Asset> findByWalletId(UUID walletId) {
		final var sql = """
			SELECT a.id, a.wallet_id, a.coin_cap_id, a.symbol, a.quantity, p.usd_value AS price
			FROM asset a LEFT JOIN price p ON a.symbol = p.asset_symbol
			WHERE a.wallet_id = :wallet_id
			""";
		final var params = Map.of("wallet_id", Objects.requireNonNull(walletId));

		return namedParameterJdbcTemplate.queryForStream(
				sql,
				params,
				(rs, n) -> new Asset(
					rs.getObject("id", UUID.class),
					rs.getObject("wallet_id", UUID.class),
					rs.getString("coin_cap_id"),
					rs.getString("symbol"),
					rs.getBigDecimal("quantity"),
					rs.getBigDecimal("price")
				))
			.toList();
	}

}
