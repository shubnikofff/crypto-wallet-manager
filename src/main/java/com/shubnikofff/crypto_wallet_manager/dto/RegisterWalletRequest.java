package com.shubnikofff.crypto_wallet_manager.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record RegisterWalletRequest(@NotBlank @Email String email) {
}
