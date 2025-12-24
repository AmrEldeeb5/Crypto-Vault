package com.example.cryptowallet.app.components

import com.example.cryptowallet.app.realtime.domain.PriceDirection

/**
 * Unified data class for displaying coin information across the app.
 * Used by CoinCard and other coin-related components.
 * 
 * @param id Unique identifier for the coin
 * @param name Full name of the coin (e.g., "Bitcoin")
 * @param symbol Ticker symbol (e.g., "BTC")
 * @param iconUrl URL for the coin's icon image
 * @param formattedPrice Formatted price string (e.g., "$45,000.00")
 * @param formattedChange Formatted change percentage (e.g., "+2.5%")
 * @param isPositive Whether the price change is positive
 * @param priceDirection Direction of price movement for animation
 * @param holdingsAmount Optional formatted holdings amount (e.g., "0.5 BTC")
 * @param holdingsValue Optional formatted holdings value (e.g., "$22,500.00")
 * @param marketCap Optional formatted market cap (e.g., "$850B")
 * @param volume24h Optional formatted 24h volume (e.g., "$25B")
 * @param isInWatchlist Whether the coin is in user's watchlist
 */
data class UiCoinItem(
    val id: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
    val formattedPrice: String,
    val formattedChange: String,
    val isPositive: Boolean,
    val priceDirection: PriceDirection,
    val holdingsAmount: String? = null,
    val holdingsValue: String? = null,
    val marketCap: String? = null,
    val volume24h: String? = null,
    val isInWatchlist: Boolean = false
) {
    /**
     * Returns true if this coin has holdings information to display.
     */
    fun hasHoldings(): Boolean = holdingsAmount != null && holdingsValue != null
}
