package com.example.cryptowallet.app.mapper

import com.example.cryptowallet.app.coins.data.remote.dto.CoinItemDto
import com.example.cryptowallet.app.coins.domain.model.CoinModel

fun CoinItemDto.toCoinModel() = CoinModel(
    uuid = uuid,
    symbol = symbol,
    name = name,
    iconUrl = iconUrl,
    price = price,
    rank = rank,
    change = change,
)