package com.shubnikofff.crypto_wallet_manager.configuration;


import com.shubnikofff.crypto_wallet_manager.integration.CoinCapClient;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


@Configuration
@EnableConfigurationProperties({IntegrationCoinCapProperties.class})
@EnableScheduling
public class ApplicationConfiguration {

    @Bean
    CoinCapClient coinCapClient(IntegrationCoinCapProperties coinCapProperties) {
        final var restClient = RestClient.builder()
            .baseUrl(coinCapProperties.baseUrl())
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(coinCapProperties.apiKey()))
            .build();

        final var restClientAdapter = RestClientAdapter.create(restClient);

        return HttpServiceProxyFactory.builderFor(restClientAdapter)
            .build()
            .createClient(CoinCapClient.class);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Wallet Manager API")
                .version("1.0")
            );
    }
}
