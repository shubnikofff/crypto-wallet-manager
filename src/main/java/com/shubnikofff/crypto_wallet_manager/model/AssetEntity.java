package com.shubnikofff.crypto_wallet_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "asset")
public class AssetEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "wallet_id", nullable = false)
//    private Wallet wallet;
    @NotNull
    @Column(name = "wallet_id", nullable = false)
    private UUID walletId;

    @NotNull
    @Column(name = "coin_cap_id", nullable = false)
    private String coinCapId;

    @Size(max = 3)
    @NotNull
    @Column(name = "symbol", nullable = false, length = 3)
    private String symbol;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

}
