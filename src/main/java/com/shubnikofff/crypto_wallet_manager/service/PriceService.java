package com.shubnikofff.crypto_wallet_manager.service;


import com.shubnikofff.crypto_wallet_manager.model.PriceEntity;
import com.shubnikofff.crypto_wallet_manager.repository.AssetRepository;
import com.shubnikofff.crypto_wallet_manager.repository.PriceJpaRepository;
import com.shubnikofff.crypto_wallet_manager.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;
    private final PriceJpaRepository priceJpaRepository;
    private final AssetRepository assetRepository;
    private final CoinCapService coinCapService;

    public void updatePrices() {

        final var ids = assetRepository.findUniqueCoinCapIds();
        final var prices = coinCapService.fetchPrices(ids);

//        priceJpaRepository.saveAll(prices.stream().map(p -> PriceEntity
//            .builder()
//            .assetSymbol(p.assetSymbol())
//            .usdValue(p.usdValue())
//            .build()
//        ).toList());

        priceRepository.saveAll(prices);
    }

}
