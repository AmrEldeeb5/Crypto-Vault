package com.example.cryptowallet.app.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.cryptowallet.theme.LocalCryptoColors
import kotlin.random.Random

@Composable
fun ChartPreview(
    isPositive: Boolean,
    modifier: Modifier = Modifier,
    seed: Int = 0
) {
    val colors = LocalCryptoColors.current
    val lineColor = if (isPositive) colors.profit else colors.loss
    val fillColor = if (isPositive) {
        Brush.verticalGradient(
            colors = listOf(
                colors.profit.copy(alpha = 0.3f),
                colors.profit.copy(alpha = 0.0f)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                colors.loss.copy(alpha = 0.3f),
                colors.loss.copy(alpha = 0.0f)
            )
        )
    }
    
    // Generate consistent data points based on seed
    val dataPoints = remember(seed, isPositive) {
        generateChartData(seed, isPositive)
    }
    
    Canvas(modifier = modifier.fillMaxSize()) {
        if (dataPoints.isEmpty()) return@Canvas
        
        val width = size.width
        val height = size.height
        val minValue = dataPoints.minOrNull() ?: 0f
        val maxValue = dataPoints.maxOrNull() ?: 1f
        val range = (maxValue - minValue).coerceAtLeast(0.01f)
        
        val stepX = width / (dataPoints.size - 1).coerceAtLeast(1)
        
        // Create path for line
        val linePath = Path()
        val fillPath = Path()
        
        dataPoints.forEachIndexed { index, value ->
            val x = index * stepX
            val y = height - ((value - minValue) / range * height)
            
            if (index == 0) {
                linePath.moveTo(x, y)
                fillPath.moveTo(x, height)
                fillPath.lineTo(x, y)
            } else {
                linePath.lineTo(x, y)
                fillPath.lineTo(x, y)
            }
        }
        
        // Close fill path
        fillPath.lineTo(width, height)
        fillPath.close()
        
        // Draw fill
        drawPath(
            path = fillPath,
            brush = fillColor
        )
        
        // Draw line
        drawPath(
            path = linePath,
            color = lineColor,
            style = Stroke(width = 2f, cap = StrokeCap.Round)
        )
    }
}

private fun generateChartData(seed: Int, isPositive: Boolean): List<Float> {
    val random = Random(seed)
    val points = mutableListOf<Float>()
    var value = 50f
    
    repeat(12) {
        // Add some randomness but bias toward the trend direction
        val change = random.nextFloat() * 10f - 5f
        val trendBias = if (isPositive) 0.5f else -0.5f
        value += change + trendBias
        value = value.coerceIn(10f, 90f)
        points.add(value)
    }
    
    return points
}
