package com.shubnikofff.crypto_wallet_manager.integration.repository;


import com.shubnikofff.crypto_wallet_manager.model.Price;
import com.shubnikofff.crypto_wallet_manager.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
public class PriceRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void should_save_all_prices_skipping_update_when_same_price_given() {
        final var prices = List.of(
            new Price("BTC", BigDecimal.valueOf(1.25)),
            new Price("ETH", BigDecimal.valueOf(0.000042))
        );

        int[] rowsAffected = priceRepository.saveAll(prices);
        assertThat(rowsAffected).isEqualTo(new int[]{1, 1});

        rowsAffected = priceRepository.saveAll(prices);
        assertThat(rowsAffected).isEqualTo(new int[]{0, 0});
    }
}
