package com.example.cryptowallet.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptowallet.theme.LocalCryptoColors
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

@Composable
fun PortfolioValueCard(
    totalValue: Double,
    change24h: Double,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val shape = RoundedCornerShape(16.dp)
    val isPositive = change24h >= 0
    val changeColor = if (isPositive) colors.profit else colors.loss
    
    // Format values
    val formattedValue = formatCurrency(totalValue)
    val formattedChange = formatPercentage(change24h)
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colors.accentBlue500.copy(alpha = 0.2f),
                        colors.accentPurple500.copy(alpha = 0.2f),
                        colors.accentPink500.copy(alpha = 0.2f)
                    )
                )
            )
            .border(1.dp, colors.cardBorder, shape)
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = "Portfolio Value",
                fontSize = 14.sp,
                color = colors.textSecondary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = formattedValue,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isPositive) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isPositive) "Trending up" else "Trending down",
                    tint = changeColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = formattedChange,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = changeColor
                )
                Text(
                    text = " (24h)",
                    fontSize = 14.sp,
                    color = colors.textTertiary
                )
            }
        }
    }
}

private fun formatCurrency(value: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return formatter.format(value)
}

private fun formatPercentage(value: Double): String {
    val sign = if (value >= 0) "+" else ""
    return "$sign${formatDecimal(value, 2)}%"
}

private fun formatDecimal(value: Double, decimals: Int): String {
    val factor = 10.0.pow(decimals)
    val rounded = kotlin.math.round(value * factor) / factor
    val parts = rounded.toString().split(".")
    val intPart = parts[0]
    val decPart = if (parts.size > 1) parts[1].take(decimals).padEnd(decimals, '0') else "0".repeat(decimals)
    return "$intPart.$decPart"
}
