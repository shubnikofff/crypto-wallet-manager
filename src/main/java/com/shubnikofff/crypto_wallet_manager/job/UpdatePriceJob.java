package com.shubnikofff.crypto_wallet_manager.job;


import com.shubnikofff.crypto_wallet_manager.service.CoinCapService;
import com.shubnikofff.crypto_wallet_manager.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePriceJob {

    private final PriceService priceService;

    @Scheduled(fixedRateString = "${job.update_price_frequency:3S}")
    void runJob() {
        priceService.updatePrices();
    }

}
