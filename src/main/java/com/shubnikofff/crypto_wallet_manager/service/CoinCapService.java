package com.shubnikofff.crypto_wallet_manager.service;


import com.shubnikofff.crypto_wallet_manager.dto.AssetCoinCapResponse;
import com.shubnikofff.crypto_wallet_manager.dto.AssetHistoryCoinCapResponse;
import com.shubnikofff.crypto_wallet_manager.integration.CoinCapClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CoinCapService implements PricingApiService {

    private final CoinCapClient coinCapClient;

    public List<AssetCoinCapResponse> searchBySymbol(String symbol) {
        return coinCapClient.getAssets(symbol).data();
    }

    public AssetCoinCapResponse fetchAsset(String id) {
        log.info("Fetching asset {}", id);
        return coinCapClient.getAssetById(id)
            .data();
    }

    public Map<String, String> getSymbolsToCoinCapIdsMap(List<String> symbols) {
        return coinCapClient.getAllAssets()
            .data()
            .stream()
            .filter(assetResponse -> symbols.contains(assetResponse.symbol()))
            .collect(Collectors.toMap(
                AssetCoinCapResponse::symbol,
                AssetCoinCapResponse::id
            ));
    }

    public AssetHistoryCoinCapResponse getAssetHistory(String assetId, Instant date) {
        return coinCapClient.getAssetHistory(
                assetId,
                "h1",
                date.minus(1, ChronoUnit.HOURS).toEpochMilli(),
                date.toEpochMilli()
            )
            .data()
            .getLast();
    }

}
