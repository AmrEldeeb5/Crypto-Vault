package com.example.cryptowallet.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Shape tokens for the CryptoWallet design system.
 * Provides consistent corner radius values for UI components.
 */
@Immutable
data class CryptoShapes(
    val card: Shape = RoundedCornerShape(12.dp),
    val button: Shape = RoundedCornerShape(8.dp),
    val chip: Shape = RoundedCornerShape(16.dp),
    val bottomSheet: Shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    val dialog: Shape = RoundedCornerShape(16.dp),
    val inputField: Shape = RoundedCornerShape(8.dp)
)

/**
 * Default shape values following the design system specification.
 */
val DefaultCryptoShapes = CryptoShapes()

/**
 * CompositionLocal for accessing CryptoShapes throughout the app.
 */
val LocalCryptoShapes = compositionLocalOf { DefaultCryptoShapes }
