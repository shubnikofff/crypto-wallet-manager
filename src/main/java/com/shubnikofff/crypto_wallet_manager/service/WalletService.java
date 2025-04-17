package com.shubnikofff.crypto_wallet_manager.service;


import com.shubnikofff.crypto_wallet_manager.dto.*;
import com.shubnikofff.crypto_wallet_manager.exception.NotFoundException;
import com.shubnikofff.crypto_wallet_manager.exception.UniqueConstraintException;
import com.shubnikofff.crypto_wallet_manager.model.Wallet;
import com.shubnikofff.crypto_wallet_manager.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final WalletRepository walletRepository;
    private final AssetService assetService;
    private final CoinCapService coinCapService;

    @Transactional
    public WalletViewDto registerWallet(RegisterWalletRequest request) throws UniqueConstraintException {
        if (walletRepository.exists(request.email())) {
            log.warn("Wallet with email {} already registered", request.email());
            throw new UniqueConstraintException("Wallet with email %s already registered".formatted(request.email()));
        }

        final var wallet = new Wallet(UUID.randomUUID(), request.email());
        walletRepository.save(wallet);

        return new WalletViewDto(
            wallet.id(),
            BigDecimal.ZERO,
            List.of()
        );
    }

    public WalletViewDto addAsset(UUID walletId, AddAssetRequest request) throws NotFoundException {
        if (!walletRepository.exists(Objects.requireNonNull(walletId))) {
            throw new NotFoundException("Wallet with id %s not found".formatted(walletId));
        }

        assetService.addAsset(walletId, request);
        return getWalletDetailedView(walletId);
    }

    public WalletViewDto getWalletDetailedView(UUID walletId) throws NotFoundException {
        if (!walletRepository.exists(Objects.requireNonNull(walletId))) {
            throw new NotFoundException("Wallet with id %s not found".formatted(walletId));
        }

        final var assetView = assetService.getAssetsForWallet(walletId).stream()
            .map(asset -> new WalletViewDto.AssetViewDto(
                asset.symbol(),
                asset.quantity(),
                asset.price(),
                asset.quantity().multiply(asset.price())
            ))
            .toList();

        final var total = assetView.stream()
            .map(WalletViewDto.AssetViewDto::value)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new WalletViewDto(
            walletId,
            total,
            assetView
        );
    }

    public WalletEvaluationResponse evaluate(Instant date, WalletEvaluationRequest request) {
        if (date.isAfter(Instant.now())) {
            throw new IllegalArgumentException("Evaluation date cannot be in after current");
        }

        final var symbols = request.assets()
            .stream()
            .map(WalletEvaluationRequest.EvaluatedAsset::symbol)
            .toList();
        final var symbolsToCoinCapIdsMap = coinCapService.getSymbolsToCoinCapIdsMap(symbols);

        if(symbols.size() != symbolsToCoinCapIdsMap.size()) {
            throw new IllegalArgumentException("Some symbols cannot be recognized by Pricing API");
        }

        final var historicalPrices = symbols.stream()
            .collect(Collectors.toMap(
                Function.identity(),
                symbol -> coinCapService.getAssetHistory(symbolsToCoinCapIdsMap.get(symbol), date).priceUsd()
            ));

        BigDecimal total = BigDecimal.ZERO;
        String bestAsset = null;
        BigDecimal bestPerformance = null;
        String worstAsset = null;
        BigDecimal worstPerformance = null;

        for(final var asset: request.assets()) {
            final var currentPrice = asset.value().divide(asset.quantity(), 10, RoundingMode.HALF_UP);
            final var historicalPrice = historicalPrices.get(asset.symbol());
            total = total.add(asset.quantity().multiply(historicalPrice));

            final var percentageDiff = currentPrice.subtract(historicalPrice)
                .divide(historicalPrice, 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

            if(bestPerformance == null || percentageDiff.compareTo(bestPerformance) > 0) {
                bestPerformance = percentageDiff;
                bestAsset = asset.symbol();
            }

            if(worstPerformance == null || percentageDiff.compareTo(worstPerformance) < 0) {
                worstPerformance = percentageDiff;
                worstAsset = asset.symbol();
            }
        }

        return new WalletEvaluationResponse(
            total,
            bestAsset,
            bestPerformance,
            worstAsset,
            worstPerformance
        );
    }
}
