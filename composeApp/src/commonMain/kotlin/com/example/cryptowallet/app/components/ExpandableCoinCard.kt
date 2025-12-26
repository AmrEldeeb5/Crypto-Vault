package com.example.cryptowallet.app.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.cryptowallet.theme.CryptoGradients
import com.example.cryptowallet.theme.LocalCryptoColors

enum class Timeframe(val label: String) {
    H1("1H"),
    H24("24H"),
    D7("7D"),
    M1("1M"),
    Y1("1Y")
}

@Composable
fun ExpandableCoinCard(
    coin: UiCoinItem,
    isExpanded: Boolean,
    selectedTimeframe: Timeframe,
    onCardClick: () -> Unit,
    onTimeframeSelected: (Timeframe) -> Unit,
    onSetAlertClick: () -> Unit,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    onWatchlistToggle: () -> Unit,
    showHoldings: Boolean = false,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val shape = RoundedCornerShape(16.dp)
    val changeColor = if (coin.isPositive) colors.profit else colors.loss
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(colors.cardBackground)
            .border(1.dp, colors.cardBorder, shape)
            .clickable(onClick = onCardClick)
    ) {
        // Main content row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Coin icon with gradient background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(CryptoGradients.coinIconGradient(coin.symbol)),
                contentAlignment = Alignment.Center
            ) {
                if (coin.iconUrl.isNotEmpty()) {
                    AsyncImage(
                        model = coin.iconUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(32.dp)
                    )
                } else {
                    Text(
                        text = coin.symbol.take(2),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Symbol and name
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = coin.symbol,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textPrimary
                )
                Text(
                    text = coin.name,
                    fontSize = 12.sp,
                    color = colors.textSecondary
                )
            }
            
            // Chart preview
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(30.dp)
            ) {
                ChartPreview(
                    isPositive = coin.isPositive,
                    seed = coin.id.hashCode()
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Price and change
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = coin.formattedPrice,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.textPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    PriceIndicator(direction = coin.priceDirection)
                }
                Text(
                    text = coin.formattedChange,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = changeColor
                )
            }
        }
        
        // Market cap row
        if (coin.marketCap != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Market Cap",
                    fontSize = 12.sp,
                    color = colors.textTertiary
                )
                Text(
                    text = coin.marketCap,
                    fontSize = 12.sp,
                    color = colors.textSecondary
                )
            }
        }
        
        // Holdings section
        if (showHoldings && coin.hasHoldings()) {
            HorizontalDivider(
                color = colors.cardBorder,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Your Holdings",
                        fontSize = 12.sp,
                        color = colors.textTertiary
                    )
                    Text(
                        text = coin.holdingsAmount ?: "",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.textPrimary
                    )
                }
                Text(
                    text = coin.holdingsValue ?: "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textPrimary
                )
            }
        }
        
        // Expanded section
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                HorizontalDivider(
                    color = colors.cardBorder,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                // Timeframe selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Timeframe.entries.forEach { timeframe ->
                        TimeframeButton(
                            timeframe = timeframe,
                            isSelected = timeframe == selectedTimeframe,
                            onClick = { onTimeframeSelected(timeframe) }
                        )
                    }
                }
                
                // Action buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Watchlist toggle
                    IconButton(
                        onClick = onWatchlistToggle,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = if (coin.isInWatchlist) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = if (coin.isInWatchlist) "Remove from watchlist" else "Add to watchlist",
                            tint = if (coin.isInWatchlist) colors.accentPink400 else colors.textSecondary
                        )
                    }
                    
                    // Set Alert button
                    OutlinedButton(
                        onClick = onSetAlertClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = colors.textPrimary
                        ),
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                            brush = Brush.horizontalGradient(
                                colors = listOf(colors.accentBlue500, colors.accentPurple500)
                            )
                        )
                    ) {
                        Text("Set Alert", fontSize = 12.sp)
                    }
                    
                    // Buy button
                    Button(
                        onClick = onBuyClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.profit
                        )
                    ) {
                        Text("Buy", fontSize = 12.sp, color = colors.backgroundPrimary)
                    }
                    
                    // Sell button (only if has holdings)
                    if (coin.hasHoldings()) {
                        Button(
                            onClick = onSellClick,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.loss
                            )
                        ) {
                            Text("Sell", fontSize = 12.sp, color = colors.backgroundPrimary)
                        }
                    }
                }
                
                // Volume and supply stats
                if (coin.volume24h != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "24h Volume",
                                fontSize = 10.sp,
                                color = colors.textTertiary
                            )
                            Text(
                                text = coin.volume24h,
                                fontSize = 12.sp,
                                color = colors.textSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimeframeButton(
    timeframe: Timeframe,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = LocalCryptoColors.current
    val shape = RoundedCornerShape(8.dp)
    
    val backgroundModifier = if (isSelected) {
        Modifier.background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    colors.accentBlue500.copy(alpha = 0.3f),
                    colors.accentPurple500.copy(alpha = 0.3f)
                )
            ),
            shape = shape
        )
    } else {
        Modifier.background(colors.cardBackgroundElevated, shape)
    }
    
    Box(
        modifier = Modifier
            .clip(shape)
            .then(backgroundModifier)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = timeframe.label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) colors.accentBlue400 else colors.textSecondary
        )
    }
}
