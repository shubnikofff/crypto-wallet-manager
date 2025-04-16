package com.shubnikofff.crypto_wallet_manager.repository;

import com.shubnikofff.crypto_wallet_manager.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, String> {
}
