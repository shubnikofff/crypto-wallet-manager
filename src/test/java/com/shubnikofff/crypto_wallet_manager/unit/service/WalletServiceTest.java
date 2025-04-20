package com.shubnikofff.crypto_wallet_manager.unit.service;

import com.shubnikofff.crypto_wallet_manager.dto.AssetHistoryCoinCapResponse;
import com.shubnikofff.crypto_wallet_manager.dto.WalletEvaluationRequest;
import com.shubnikofff.crypto_wallet_manager.dto.WalletEvaluationRequest.EvaluatedAsset;
import com.shubnikofff.crypto_wallet_manager.repository.WalletRepository;
import com.shubnikofff.crypto_wallet_manager.service.AssetService;
import com.shubnikofff.crypto_wallet_manager.service.CoinCapService;
import com.shubnikofff.crypto_wallet_manager.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WalletServiceTest {

	private WalletService walletService;

	private WalletRepository walletRepository;

	private AssetService assetService;

	private CoinCapService coinCapService;

	@BeforeEach
	void setUp() {
		walletRepository = mock(WalletRepository.class);
		assetService = mock(AssetService.class);
		coinCapService = mock(CoinCapService.class);

		walletService = new WalletService(
			walletRepository,
			assetService,
			coinCapService
		);
	}

	@Test
	void should_evaluate_wallet_success_when_valid_date_given() {
		final var date = Instant.now().minus(1, ChronoUnit.DAYS);
		final var evaluationRequest = new WalletEvaluationRequest(List.of(
			new EvaluatedAsset("BTC", BigDecimal.valueOf(0.5), BigDecimal.valueOf(35000)),
			new EvaluatedAsset("ETH", BigDecimal.valueOf(4.25), BigDecimal.valueOf(15310.71))
		));

		when(coinCapService.getSymbolsToCoinCapIdsMap(List.of("BTC", "ETH")))
			.thenReturn(Map.of("BTC", "bitcoin", "ETH", "ethereum"));

		when(coinCapService.getAssetHistory("bitcoin", date))
			.thenReturn(new AssetHistoryCoinCapResponse(new BigDecimal("84602.2572006"), date));

		when(coinCapService.getAssetHistory("ethereum", date))
			.thenReturn(new AssetHistoryCoinCapResponse(new BigDecimal("1583.77469219"), date));

		final var evaluation = walletService.evaluate(date, evaluationRequest);

		assertThat(evaluation)
			.hasFieldOrPropertyWithValue("total", BigDecimal.valueOf(49032.1710421075))
			.hasFieldOrPropertyWithValue("bestAsset", "ETH")
			.hasFieldOrPropertyWithValue("bestPerformance", new BigDecimal("127.4641726400"))
			.hasFieldOrPropertyWithValue("worstAsset", "BTC")
			.hasFieldOrPropertyWithValue("worstPerformance", new BigDecimal("-17.2598907900"));
	}
}
