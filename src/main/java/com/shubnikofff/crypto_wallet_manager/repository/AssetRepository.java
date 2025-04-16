package com.shubnikofff.crypto_wallet_manager.repository;


import com.shubnikofff.crypto_wallet_manager.model.Asset;
import com.shubnikofff.crypto_wallet_manager.model.AssetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AssetRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final JdbcTemplate jdbcTemplate;

    public List<String> findUniqueCoinCapIds() {
        return jdbcTemplate.queryForList("SELECT DISTINCT coin_cap_id FROM asset", String.class);
    }

    public List<Asset> findByWalletId() {

    }

}
