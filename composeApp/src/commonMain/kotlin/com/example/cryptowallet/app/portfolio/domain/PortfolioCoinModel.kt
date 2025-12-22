package com.example.cryptowallet.app.portfolio.domain

import com.example.cryptowallet.app.core.domain.coin.Coin


data class PortfolioCoinModel(
    val coin: Coin,
    val performancePercent: Double,
    val averagePurchasePrice: Double,
    val ownedAmountInUnit: Double,
    val ownedAmountInFiat: Double,
)