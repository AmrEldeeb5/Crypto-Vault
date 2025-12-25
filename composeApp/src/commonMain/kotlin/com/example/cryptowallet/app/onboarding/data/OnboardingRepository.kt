package com.example.cryptowallet.app.onboarding.data

import com.example.cryptowallet.app.onboarding.presentation.OnboardingState
import com.example.cryptowallet.app.watchlist.domain.WatchlistRepository
import kotlinx.coroutines.flow.first

/**
 * Repository for managing onboarding state persistence.
 * Uses in-memory storage for MVP (can be replaced with DataStore later).
 */
class OnboardingRepository(
    private val watchlistRepository: WatchlistRepository
) {
    // In-memory storage for MVP
    // TODO: Replace with DataStore for production
    private var savedCurrentStep: Int = 0
    private var savedSelectedCoins: Set<String> = emptySet()
    private var savedNotificationsEnabled: Boolean = false
    private var onboardingCompleted: Boolean = false
    
    /**
     * Get the saved onboarding state.
     */
    suspend fun getOnboardingState(): OnboardingState {
        return OnboardingState(
            currentStep = savedCurrentStep,
            selectedCoins = savedSelectedCoins,
            notificationsEnabled = savedNotificationsEnabled,
            isLoading = false
        )
    }
    
    /**
     * Save the current onboarding state.
     */
    suspend fun saveOnboardingState(state: OnboardingState) {
        savedCurrentStep = state.currentStep
        savedSelectedCoins = state.selectedCoins
        savedNotificationsEnabled = state.notificationsEnabled
    }
    
    /**
     * Set whether onboarding has been completed.
     */
    suspend fun setOnboardingCompleted(completed: Boolean) {
        onboardingCompleted = completed
    }
    
    /**
     * Check if onboarding has been completed.
     */
    suspend fun isOnboardingCompleted(): Boolean {
        return onboardingCompleted
    }
    
    /**
     * Save selected coins to the watchlist.
     */
    suspend fun saveSelectedCoinsToWatchlist(coins: Set<String>) {
        // Map coin symbols to coin IDs (lowercase for API compatibility)
        val coinIdMap = mapOf(
            "BTC" to "bitcoin",
            "ETH" to "ethereum",
            "BNB" to "binancecoin",
            "SOL" to "solana",
            "ADA" to "cardano",
            "XRP" to "ripple"
        )
        
        coins.forEach { symbol ->
            val coinId = coinIdMap[symbol] ?: symbol.lowercase()
            try {
                watchlistRepository.addToWatchlist(coinId)
            } catch (e: Exception) {
                // Continue even if one fails
            }
        }
    }
}
