package com.shubnikofff.crypto_wallet_manager.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "price")
public class PriceEntity {

    @Id
    @Size(max = 3)
    @Column(name = "asset_symbol", nullable = false, length = 3)
    private String assetSymbol;

    @NotNull
    @Column(name = "usd_value", nullable = false)
    private BigDecimal usdValue;

}
