package com.example.cryptowallet.app.onboarding.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.cryptowallet.theme.LocalCryptoColors
import kotlin.random.Random

/**
 * Animated background for the onboarding flow.
 * Features pulsing gradient blobs and floating crypto symbols.
 * 
 * Requirements: 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7
 */
@Composable
fun OnboardingBackground(
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.backgroundPrimary)
    ) {
        // Gradient blobs
        GradientBlob(
            colors = listOf(colors.accentBlue500, colors.accentBlue600),
            offsetX = -100,
            offsetY = -50,
            size = 300,
            animationDuration = 4000
        )
        
        GradientBlob(
            colors = listOf(colors.accentPurple500, colors.accentPurple600),
            offsetX = 200,
            offsetY = 500,
            size = 350,
            animationDuration = 5000
        )
        
        GradientBlob(
            colors = listOf(colors.accentPink500, colors.accentPink400),
            offsetX = 50,
            offsetY = 300,
            size = 250,
            animationDuration = 4500
        )
        
        // Floating crypto symbols
        FloatingCryptoSymbols()
    }
}

/**
 * Pulsing gradient blob with blur effect.
 */
@Composable
private fun GradientBlob(
    colors: List<androidx.compose.ui.graphics.Color>,
    offsetX: Int,
    offsetY: Int,
    size: Int,
    animationDuration: Int
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    Box(
        modifier = Modifier
            .offset(x = offsetX.dp, y = offsetY.dp)
            .size(size.dp)
            .scale(scale)
            .blur(80.dp)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = colors.map { it.copy(alpha = 0.3f) }
                )
            )
    )
}

/**
 * Floating crypto symbols with random positions and animations.
 */
@Composable
private fun FloatingCryptoSymbols() {
    val symbols = listOf("₿", "Ξ", "◎", "₳", "Ł")
    
    // Generate random positions for 15 symbols
    val symbolData = remember {
        (0 until 15).map {
            SymbolData(
                symbol = symbols[it % symbols.size],
                x = Random.nextInt(0, 350),
                y = Random.nextInt(0, 700),
                duration = Random.nextInt(3000, 6000),
                delay = Random.nextInt(0, 2000)
            )
        }
    }
    
    symbolData.forEach { data ->
        FloatingSymbol(data)
    }
}

private data class SymbolData(
    val symbol: String,
    val x: Int,
    val y: Int,
    val duration: Int,
    val delay: Int
)

@Composable
private fun FloatingSymbol(data: SymbolData) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(data.duration, delayMillis = data.delay),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    Text(
        text = data.symbol,
        modifier = Modifier
            .offset(x = data.x.dp, y = (data.y + offsetY).dp)
            .alpha(0.05f),
        color = androidx.compose.ui.graphics.Color.White
    )
}
