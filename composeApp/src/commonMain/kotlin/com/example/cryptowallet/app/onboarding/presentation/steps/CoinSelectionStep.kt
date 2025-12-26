package com.example.cryptowallet.app.onboarding.presentation.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptowallet.app.onboarding.domain.popularCoins
import com.example.cryptowallet.app.onboarding.presentation.components.CoinSelectionCard
import com.example.cryptowallet.theme.LocalCryptoColors
import com.example.cryptowallet.theme.LocalCryptoTypography

@Composable
fun CoinSelectionStep(
    selectedCoins: Set<String>,
    onToggleCoin: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoinSelectionHeader()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 2x3 grid of coin cards
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (rowIndex in 0 until 3) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (colIndex in 0 until 2) {
                        val coinIndex = rowIndex * 2 + colIndex
                        if (coinIndex < popularCoins.size) {
                            val coin = popularCoins[coinIndex]
                            CoinSelectionCard(
                                coin = coin,
                                isSelected = selectedCoins.contains(coin.symbol),
                                onToggle = { onToggleCoin(coin.symbol) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        SelectionBadge(selectedCount = selectedCoins.size)
    }
}

@Composable
fun CoinSelectionHeader(
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // BarChart icon section
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(colors.accentPink500, colors.loss)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "📊", fontSize = 40.sp)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Choose Your Favorites",
            style = typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Select coins to add to your watchlist",
            style = typography.bodyMedium,
            color = colors.textSecondary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = "You can always change this later",
            style = typography.bodySmall,
            color = colors.textTertiary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SelectionBadge(
    selectedCount: Int,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    
    if (selectedCount > 0) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(colors.profit.copy(alpha = 0.1f))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "✓ $selectedCount ${if (selectedCount == 1) "coin" else "coins"} selected",
                style = typography.bodyMedium,
                color = colors.profit,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun getSelectionCountText(count: Int): String {
    return when {
        count == 0 -> "Select at least one coin to continue"
        count == 1 -> "1 coin selected"
        else -> "$count coins selected"
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
fun CoinSelectionHeaderPreview() {
    com.example.cryptowallet.theme.CoinRoutineTheme {
        Box(modifier = Modifier.background(Color(0xFF0F172A)).padding(24.dp)) {
            CoinSelectionHeader()
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
fun SelectionBadgePreview() {
    com.example.cryptowallet.theme.CoinRoutineTheme {
        Box(modifier = Modifier.background(Color(0xFF0F172A)).padding(24.dp)) {
            SelectionBadge(selectedCount = 5)
        }
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
fun CoinSelectionStepPreview() {
    com.example.cryptowallet.theme.CoinRoutineTheme {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.background(Color(0xFF0F172A))
        ) {
            CoinSelectionStep(
                selectedCoins = setOf("BTC", "ETH"),
                onToggleCoin = {}
            )
        }
    }
}
