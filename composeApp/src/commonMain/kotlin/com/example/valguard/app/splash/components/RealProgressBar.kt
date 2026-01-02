package com.example.valguard.app.splash.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.valguard.app.splash.domain.InitPhase
import kotlin.math.roundToInt

/**
 * Real progress bar with phase-specific status text.
 * Shows actual initialization progress, not simulated.
 * 
 * De-emphasized design: progress = reassurance, not center stage.
 */
@Composable
fun RealProgressBar(
    progress: Float,
    phase: InitPhase,
    modifier: Modifier = Modifier
) {
    val percentage = (progress * 100).roundToInt()
    
    // Status text WITHOUT animated ellipsis - let progress bar be the only feedback
    val statusText = when (phase) {
        is InitPhase.SecureStorage -> "Setting up secure storage"
        is InitPhase.Database -> "Preparing database"
        is InitPhase.Network -> "Connecting to network"
        is InitPhase.Config -> "Loading configuration"
        is InitPhase.UIReady -> "Almost ready"
    }
    
    Column(
        modifier = modifier.fillMaxWidth(0.72f), // Wider bar (70-75% of screen)
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress percentage and label
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF94A3B8).copy(alpha = 0.5f) // Reduced opacity (was 0.7f)
            )
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFFCBD5E1).copy(alpha = 0.8f) // Slightly reduced (was 1.0f)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp)) // Tighter spacing
        
        // Status text - moved above bar (Option A: cleaner, more system-like)
        Text(
            text = statusText,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF64748B).copy(alpha = 0.8f),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Progress bar - reduced height for de-emphasis
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp) // Reduced from 8dp
                .clip(RoundedCornerShape(3.dp)) // Adjusted corner radius
                .background(Color(0xFF1E293B).copy(alpha = 0.45f)) // Further reduced (was 0.6f) - bar emerges, not sits in container
                .semantics {
                    progressBarRangeInfo = androidx.compose.ui.semantics.ProgressBarRangeInfo(
                        current = progress,
                        range = 0f..1f
                    )
                }
        ) {
            // Gradient fill with slightly reduced opacity
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF2563EB).copy(alpha = 0.9f), // Keep fill exactly as-is
                                Color(0xFF7C3AED).copy(alpha = 0.9f)  // Keep fill exactly as-is
                            )
                        )
                    )
            )
        }
    }
}
