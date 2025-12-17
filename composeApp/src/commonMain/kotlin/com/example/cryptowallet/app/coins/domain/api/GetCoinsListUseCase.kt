package com.example.cryptowallet.app.coins.domain.api

import com.example.cryptowallet.app.coins.data.remote.dto.toCoinModel
import com.example.cryptowallet.app.coins.domain.CoinsRemoteDataSource
import com.example.cryptowallet.app.coins.domain.model.CoinModel
import com.example.cryptowallet.app.core.domain.DataError
import com.example.cryptowallet.app.core.domain.Result
import com.example.cryptowallet.app.core.domain.map


class GetCoinsListUseCase(
    private val client: CoinsRemoteDataSource,
) {

    suspend fun execute(): Result<List<CoinModel>, DataError.Remote> {
        return client.getListOfCoins().map { dto ->
            dto.data.coins.map { it.toCoinModel() }
        }
    }
}