package com.shubnikofff.crypto_wallet_manager.service;


import com.shubnikofff.crypto_wallet_manager.dto.AddAssetRequest;
import com.shubnikofff.crypto_wallet_manager.exception.UniqueConstraintException;
import com.shubnikofff.crypto_wallet_manager.model.Asset;
import com.shubnikofff.crypto_wallet_manager.model.Price;
import com.shubnikofff.crypto_wallet_manager.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AssetService {

	private final AssetRepository assetRepository;
	private final CoinCapService coinCapService;
	private final PriceService priceService;

	public Set<String> getCoinCapIds() {
		final var ids = assetRepository.findUniqueCoinCapIds();

		return new HashSet<>(ids);
	}

	public List<Asset> getAssetsForWallet(UUID walletId) {
		return assetRepository.findByWalletId(Objects.requireNonNull(walletId));
	}

	@Transactional
	public void addAsset(UUID walletId, AddAssetRequest request) throws UniqueConstraintException {
		final var assetCoinCapResponse = coinCapService.searchBySymbol(Objects.requireNonNull(request).symbol())
			.stream()
			.filter(assetResponse -> assetResponse.symbol().equals(request.symbol()) && assetResponse.priceUsd().compareTo(request.price()) == 0)
			.findFirst()
			.orElseThrow(() -> new UniqueConstraintException("Price is not match. Skip transaction"));

		final var asset = assetRepository.findByWalletIdAndSymbolForUpdate(Objects.requireNonNull(walletId), request.symbol())
			.map(a -> a.withQuantity(a.quantity().add(request.quantity())))
			.orElseGet(() -> new Asset(
				UUID.randomUUID(),
				walletId,
				assetCoinCapResponse.id(),
				request.symbol(),
				request.quantity(),
				request.price()
			));

		priceService.save(new Price(asset.symbol(), assetCoinCapResponse.priceUsd()));
		assetRepository.save(asset);
	}
}
