package com.example.cryptowallet.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Spacing tokens for the CryptoWallet design system.
 * Provides consistent spacing values for margins, padding, and gaps.
 */
@Immutable
data class CryptoSpacing(
    val xxs: Dp = 4.dp,
    val xs: Dp = 8.dp,
    val sm: Dp = 12.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 48.dp
)

/**
 * Default spacing values following the design system specification.
 */
val DefaultCryptoSpacing = CryptoSpacing()

/**
 * CompositionLocal for accessing CryptoSpacing throughout the app.
 */
val LocalCryptoSpacing = compositionLocalOf { DefaultCryptoSpacing }
