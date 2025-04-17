package com.shubnikofff.crypto_wallet_manager.integration;


import com.shubnikofff.crypto_wallet_manager.dto.AssetCoinCapResponse;
import com.shubnikofff.crypto_wallet_manager.dto.AssetHistoryCoinCapResponse;
import com.shubnikofff.crypto_wallet_manager.dto.CoinCapResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface CoinCapClient {

    @GetExchange("/assets")
    CoinCapResponseBody<List<AssetCoinCapResponse>> getAssets(@RequestParam("search") String search);

    @GetExchange("/assets")
    CoinCapResponseBody<List<AssetCoinCapResponse>> getAllAssets();

    @GetExchange("/assets/{id}")
    CoinCapResponseBody<AssetCoinCapResponse> getAssetById(@PathVariable("id") String id);

    @GetExchange("/assets/{id}/history")
    CoinCapResponseBody<List<AssetHistoryCoinCapResponse>> getAssetHistory(
        @PathVariable("id") String id,
        @RequestParam("interval") String interval,
        @RequestParam("start") long start,
        @RequestParam("end") long end
    );

}
