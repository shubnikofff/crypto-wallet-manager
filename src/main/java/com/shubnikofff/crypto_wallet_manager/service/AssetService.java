package com.shubnikofff.crypto_wallet_manager.service;


import com.shubnikofff.crypto_wallet_manager.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssetService {

	private final AssetRepository assetRepository;

	public Set<String> getCoinCapIds() {
		final var ids = assetRepository.findUniqueCoinCapIds();

		return new HashSet<>(ids);
	}

}
