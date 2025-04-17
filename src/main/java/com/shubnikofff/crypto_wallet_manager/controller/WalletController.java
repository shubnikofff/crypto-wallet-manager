package com.shubnikofff.crypto_wallet_manager.controller;


import com.shubnikofff.crypto_wallet_manager.dto.*;
import com.shubnikofff.crypto_wallet_manager.exception.UniqueConstraintException;
import com.shubnikofff.crypto_wallet_manager.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RestController("api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    @Operation(summary = "Register new wallet with a given email")
    public ResponseEntity<WalletViewDto> registerWallet(@RequestBody @Valid RegisterWalletRequest request) {
        final var response = walletService.registerWallet(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @Operation(summary = "Add asset to the given wallet")
    public ResponseEntity<WalletViewDto> addAsset(@PathVariable("id") UUID walletId, @RequestBody @Valid AddAssetRequest request) {
        final var walletView = walletService.addAsset(walletId, request);
        return ResponseEntity.ok(walletView);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get wallet detailed view by its id")
    public ResponseEntity<WalletViewDto> getWalletDetailedView(@PathVariable("id") UUID walletId) {
        final var walletView = walletService.getWalletDetailedView(walletId);
        return ResponseEntity.ok(walletView);
    }

    @PostMapping("evaluation")
    @Operation(summary = "Create wallet evaluation")
    public ResponseEntity<WalletEvaluationResponse> evaluateWalletPerformance(
        @RequestParam(name = "date", required = false) Instant date,
        @RequestBody @Valid WalletEvaluationRequest request
    ) {
        final var evaluation = walletService.evaluate(Optional.ofNullable(date).orElseGet(Instant::now), request);
        return ResponseEntity.ok(evaluation);
    }

    @ExceptionHandler(UniqueConstraintException.class)
    public ResponseEntity<String> exceptionHandler(UniqueConstraintException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
