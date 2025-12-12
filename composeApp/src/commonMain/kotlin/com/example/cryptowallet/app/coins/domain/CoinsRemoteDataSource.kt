package com.example.cryptowallet.app.coins.domain

import com.example.cryptowallet.app.coins.data.remote.dto.CoinDetailsResponseDto
import com.example.cryptowallet.app.coins.data.remote.dto.CoinPriceHistoryResponseDto
import com.example.cryptowallet.app.coins.data.remote.dto.CoinResponseDto
import com.example.cryptowallet.app.core.domain.DataError
import com.example.cryptowallet.app.core.domain.Result


interface CoinsRemoteDataSource {

    suspend fun getListOfCoins(): Result<CoinResponseDto, DataError.Remote>

    suspend fun getCoinPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto,DataError.Remote>

    suspend fun getCoinById(coinId: String): Result<CoinDetailsResponseDto, DataError.Remote>
}