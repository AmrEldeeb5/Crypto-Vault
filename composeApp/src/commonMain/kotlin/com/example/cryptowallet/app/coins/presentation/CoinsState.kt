package com.example.cryptowallet.app.coins.presentation

import androidx.compose.runtime.Stable
import org.jetbrains.compose.resources.StringResource


@Stable
data class CoinsState(
    val isLoading: Boolean = true,
    val error: StringResource? = null,
    val coins: List<UiCoinListItem> = emptyList(),
)