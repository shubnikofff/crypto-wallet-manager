package com.shubnikofff.crypto_wallet_manager.repository;


import com.shubnikofff.crypto_wallet_manager.model.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface WalletRepository extends CrudRepository<Wallet, UUID> {

	boolean existsById(UUID id);

	boolean existsByOwnerEmail(String email);
}
