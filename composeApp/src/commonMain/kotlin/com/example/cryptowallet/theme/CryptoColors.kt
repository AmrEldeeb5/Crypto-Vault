package com.example.cryptowallet.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Semantic color tokens for the CryptoWallet design system.
 * Provides consistent colors across the app with light/dark theme support.
 */
@Immutable
data class CryptoColors(
    // Profit/Loss semantic colors
    val profit: Color,
    val loss: Color,
    val neutral: Color,
    
    // Card and surface colors
    val cardBackground: Color,
    val cardBackgroundElevated: Color,
    
    // Text colors
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    
    // Status colors
    val statusConnected: Color,
    val statusConnecting: Color,
    val statusError: Color,
    
    // Interactive element colors
    val buttonPrimary: Color,
    val buttonSecondary: Color,
    val buttonDisabled: Color,
    
    // Divider and border colors
    val divider: Color,
    val border: Color,
    
    // Shimmer/skeleton colors
    val shimmerBase: Color,
    val shimmerHighlight: Color
)

// Light theme colors
val LightCryptoColors = CryptoColors(
    profit = Color(0xFF1B8A4B),           // Darker green for better contrast on white
    loss = Color(0xFFD2122E),             // Red
    neutral = Color(0xFF717970),          // Gray
    
    cardBackground = Color(0xFFFFFFFF),
    cardBackgroundElevated = Color(0xFFF3F3F4),
    
    textPrimary = Color(0xFF1A1C1C),
    textSecondary = Color(0xFF404941),
    textTertiary = Color(0xFF717970),
    
    statusConnected = Color(0xFF1B8A4B),  // Match profit color
    statusConnecting = Color(0xFF457B9D),
    statusError = Color(0xFFBB152C),
    
    buttonPrimary = Color(0xFF485F84),
    buttonSecondary = Color(0xFFD5E3FF),
    buttonDisabled = Color(0xFFE2E2E2),
    
    divider = Color(0xFFE2E2E2),
    border = Color(0xFFC0C9BE),
    
    shimmerBase = Color(0xFFE8E8E8),
    shimmerHighlight = Color(0xFFF3F3F4)
)

// Dark theme colors
val DarkCryptoColors = CryptoColors(
    profit = Color(0xFF32DE84),           // Bright green (same in dark)
    loss = Color(0xFFFFB3B1),             // Lighter red for dark mode
    neutral = Color(0xFF8A9389),          // Lighter gray
    
    cardBackground = Color(0xFF1E2020),
    cardBackgroundElevated = Color(0xFF282A2B),
    
    textPrimary = Color(0xFFE2E2E2),
    textSecondary = Color(0xFFC0C9BE),
    textTertiary = Color(0xFF8A9389),
    
    statusConnected = Color(0xFF32DE84),
    statusConnecting = Color(0xFF98CDF2),
    statusError = Color(0xFFFFB3B1),
    
    buttonPrimary = Color(0xFFB0C7F1),
    buttonSecondary = Color(0xFF30476A),
    buttonDisabled = Color(0xFF404941),
    
    divider = Color(0xFF333535),
    border = Color(0xFF404941),
    
    shimmerBase = Color(0xFF282A2B),
    shimmerHighlight = Color(0xFF333535)
)

/**
 * CompositionLocal for accessing CryptoColors throughout the app.
 */
val LocalCryptoColors = compositionLocalOf { LightCryptoColors }
