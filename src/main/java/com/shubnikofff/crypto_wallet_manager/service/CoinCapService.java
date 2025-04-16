package com.shubnikofff.crypto_wallet_manager.service;


import com.shubnikofff.crypto_wallet_manager.dto.AssetCoinCapResponse;
import com.shubnikofff.crypto_wallet_manager.dto.CoinCapResponseBody;
import com.shubnikofff.crypto_wallet_manager.integration.CoinCapClient;
import com.shubnikofff.crypto_wallet_manager.model.Price;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CoinCapService implements PricingApiService {

    private final CoinCapClient coinCapClient;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public List<AssetCoinCapResponse> searchAsset() {
        return coinCapClient.getAssets("BTC").data();
    }

    public Optional<Price> fetchPrice(String id) {
        return coinCapClient.getAssetsByIds(id).data()
            .stream()
            .findFirst()
            .map(asset -> new Price(
                asset.symbol(),
                asset.priceUsd()
            ));
    }

    public List<Price> fetchPrices(List<String> assetIds) {
        final var futures = assetIds.stream()
            .map(id -> CompletableFuture.supplyAsync(() -> {
                    log.info("Fetching price for {}", id);
                    return coinCapClient.getAssetsByIds(id);
                }, executorService)
                .thenApply(CoinCapService::map))
            .toList();

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
            .thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .toList())
            .exceptionally(throwable -> {
                log.error("Error while fetching prices", throwable);
                return List.of();
            })
            .join();
    }

//    private static List<Price> map(CoinCapResponseBody<AssetCoinCapResponse> response) {
//        return Objects.requireNonNull(response).data()
//            .stream()
//            .map(asset -> new Price(
//                asset.symbol(),
//                asset.priceUsd()
//            ))
//            .toList();
//    }

    private static Price map(CoinCapResponseBody<AssetCoinCapResponse> response) {
        return Objects.requireNonNull(response).data()
            .stream()
            .findFirst()
            .map(asset -> new Price(
                asset.symbol(),
                asset.priceUsd()
            ))
            .orElse(null);
    }
}
