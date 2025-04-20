package com.shubnikofff.crypto_wallet_manager.integration.service;


import com.shubnikofff.crypto_wallet_manager.service.CoinCapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@EnableWireMock(@ConfigureWireMock(usePortFromPredefinedPropertyIfFound = true))
public class CoinCapServiceTest {

	@Autowired
	private CoinCapService coinCapService;

	@Test
	void should_fetch_asset_success_when_api_respond_ok() {
		stubFor(get("/assets/bitcoin").willReturn(
				aResponse()
					.withStatus(200)
					.withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
					.withBody("""
					{
						"data": {
							"id": "bitcoin",
							"rank": "1",
							"symbol": "BTC",
							"name": "Bitcoin",
							"supply": "19853946.0000000000000000",
							"maxSupply": "21000000.0000000000000000",
							"marketCapUsd": "1678540183162.9332400626818922",
							"volumeUsd24Hr": "4365530470.4608407076985766",
							"priceUsd": "84544.4116329788164057",
							"changePercent24Hr": "-0.8313229196557990",
							"vwap24Hr": "84774.4917113574503078",
							"explorer": "https://blockchain.info/"
						},
						"timestamp": 1745174386669
					}
					""")
			)
		);

		final var asset = coinCapService.fetchAsset("bitcoin");
		assertThat(asset)
			.hasFieldOrPropertyWithValue("id", "bitcoin")
			.hasFieldOrPropertyWithValue("symbol", "BTC")
			.hasFieldOrPropertyWithValue("priceUsd", new BigDecimal("84544.4116329788164057"));
	}

}
