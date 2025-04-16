package com.shubnikofff.crypto_wallet_manager.service;


import com.shubnikofff.crypto_wallet_manager.model.Price;
import com.shubnikofff.crypto_wallet_manager.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PriceService {

	private final ExecutorService executorService = Executors.newFixedThreadPool(3);

	private final PriceRepository priceRepository;
	private final AssetService assetService;
	private final CoinCapService coinCapService;

	@Transactional
	public void updatePrices() {
		final var futures = assetService.getCoinCapIds()
			.stream()
			.map(id -> CompletableFuture.supplyAsync(() -> coinCapService.fetchAsset(id), executorService)
				.thenApply(response -> new Price(response.symbol(), response.priceUsd()))
				.exceptionally(throwable -> {
					log.error("Fail to fetch asset {}", id, throwable);
					return null;
				})
			)
			.toList();

		final var newPrices = CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
			.thenApply(v ->
				futures.stream()
					.map(CompletableFuture::join)
					.toList()
			)
			.exceptionally(throwable -> {
				log.error("Fail to fetch new prices ", throwable);
				return List.of();
			})
			.join();

		priceRepository.saveAll(newPrices);
	}
}
