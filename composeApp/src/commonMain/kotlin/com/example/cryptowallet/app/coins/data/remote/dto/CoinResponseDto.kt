package com.example.cryptowallet.app.coins.data.remote.dto

import com.example.cryptowallet.app.coins.domain.model.CoinModel
import kotlinx.serialization.Serializable

@Serializable
data class CoinResponseDto(
    val data: CoinsListDto
)

@Serializable
data class CoinsListDto(
    val coins: List<CoinItemDto>
)

@Serializable
data class CoinItemDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val price: Double,
    val rank: Int,
    val change: Double
)

fun CoinItemDto.toCoinModel() = CoinModel(
    uuid = uuid,
    symbol = symbol,
    name = name,
    iconUrl = iconUrl,
    price = price,
    rank = rank,
    change = change
)
