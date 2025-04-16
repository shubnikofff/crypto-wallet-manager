package com.shubnikofff.crypto_wallet_manager.configuration;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

@ConfigurationProperties(prefix = "integration.coin-cap")
@Validated
public record IntegrationCoinCapProperties(
    @NotBlank String apiKey,
    @NotNull URI baseUrl
    ) {
}
