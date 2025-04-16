package com.shubnikofff.crypto_wallet_manager.service;


import com.shubnikofff.crypto_wallet_manager.dto.AssetCoinCapResponse;
import com.shubnikofff.crypto_wallet_manager.integration.CoinCapClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CoinCapService implements PricingApiService {

	private final CoinCapClient coinCapClient;

	public List<AssetCoinCapResponse> searchAsset(String search) {
		return coinCapClient.getAssets(search).data();
	}

	public AssetCoinCapResponse fetchAsset(String id) {
		log.info("Fetching asset {}", id);
		return coinCapClient.getAssetsByIds(id, 1)
			.data()
			.getFirst();
	}
}
