package com.shubnikofff.crypto_wallet_manager.integration;


import com.shubnikofff.crypto_wallet_manager.dto.AssetCoinCapResponse;
import com.shubnikofff.crypto_wallet_manager.dto.CoinCapResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface CoinCapClient {

    @GetExchange("/assets")
    CoinCapResponseBody<AssetCoinCapResponse> getAssets(
        @RequestParam("search") String search
    );

    @GetExchange("/assets")
    CoinCapResponseBody<AssetCoinCapResponse> getAssetsByIds(
        @RequestParam("ids") String search
    );
}
