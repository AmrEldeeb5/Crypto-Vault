package com.example.cryptowallet.app.onboarding.domain

import androidx.compose.ui.graphics.Color

/**
 * Represents the 4 steps in the onboarding flow.
 * Each step has a unique index and gradient colors for styling.
 */
sealed class OnboardingStep(
    val index: Int,
    val gradientColors: List<Color>
) {
    /**
     * Step 1: Welcome screen with brand introduction
     * Gradient: Blue → Purple
     */
    data object Welcome : OnboardingStep(
        index = 0,
        gradientColors = listOf(
            Color(0xFF3B82F6), // blue-500
            Color(0xFFA855F7)  // purple-500
        )
    )
    
    /**
     * Step 2: Features grid showing app capabilities
     * Gradient: Purple → Pink
     */
    data object Features : OnboardingStep(
        index = 1,
        gradientColors = listOf(
            Color(0xFFA855F7), // purple-500
            Color(0xFFEC4899)  // pink-500
        )
    )
    
    /**
     * Step 3: Coin selection for watchlist
     * Gradient: Pink → Rose
     */
    data object CoinSelection : OnboardingStep(
        index = 2,
        gradientColors = listOf(
            Color(0xFFEC4899), // pink-500
            Color(0xFFFB7185)  // rose-400
        )
    )
    
    /**
     * Step 4: Notification preferences
     * Gradient: Emerald → Teal
     */
    data object Notifications : OnboardingStep(
        index = 3,
        gradientColors = listOf(
            Color(0xFF34D399), // emerald-400
            Color(0xFF14B8A6)  // teal-500
        )
    )
    
    companion object {
        const val TOTAL_STEPS = 4
        
        /**
         * Get OnboardingStep from index.
         * Returns Welcome for invalid indices.
         */
        fun fromIndex(index: Int): OnboardingStep = when (index) {
            0 -> Welcome
            1 -> Features
            2 -> CoinSelection
            3 -> Notifications
            else -> Welcome
        }
        
        /**
         * Get all steps in order.
         */
        fun all(): List<OnboardingStep> = listOf(Welcome, Features, CoinSelection, Notifications)
    }
}
