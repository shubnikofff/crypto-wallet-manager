package com.shubnikofff.crypto_wallet_manager.job;


import com.shubnikofff.crypto_wallet_manager.service.AssetService;
import com.shubnikofff.crypto_wallet_manager.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePriceJob {

    private final AssetService assetService;
    private final PriceService priceService;

    @Scheduled(initialDelay = 1000, fixedRateString = "${job.update_price_frequency:3S}")
    void runJob() {
        final var coinCapIds = assetService.getCoinCapIds();
        priceService.updatePrices(coinCapIds);
    }

}
