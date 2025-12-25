package com.example.cryptowallet.app.onboarding.domain

import androidx.compose.ui.graphics.Color

/**
 * Enum representing icon types for onboarding features.
 * Using enum instead of ImageVector for better cross-platform compatibility.
 */
enum class OnboardingIcon(val emoji: String) {
    TRENDING_UP("📈"),
    NOTIFICATIONS("🔔"),
    WALLET("💰"),
    SHIELD("🛡️"),
    SPARKLES("✨")
}

/**
 * Represents a feature highlight shown during onboarding.
 */
data class OnboardingFeature(
    val iconType: OnboardingIcon,
    val title: String,
    val description: String,
    val gradientColors: List<Color>
)

/**
 * Features displayed on the Welcome step (Step 1).
 * Three vertical cards highlighting key app capabilities.
 */
val welcomeFeatures = listOf(
    OnboardingFeature(
        iconType = OnboardingIcon.TRENDING_UP,
        title = "Track 5000+ Cryptos",
        description = "Real-time market data",
        gradientColors = listOf(
            Color(0xFF34D399), // emerald-400
            Color(0xFF14B8A6)  // teal-500
        )
    ),
    OnboardingFeature(
        iconType = OnboardingIcon.NOTIFICATIONS,
        title = "Smart Price Alerts",
        description = "Never miss opportunities",
        gradientColors = listOf(
            Color(0xFF3B82F6), // blue-500
            Color(0xFFA855F7)  // purple-500
        )
    ),
    OnboardingFeature(
        iconType = OnboardingIcon.WALLET,
        title = "Portfolio Management",
        description = "Track your investments",
        gradientColors = listOf(
            Color(0xFFEC4899), // pink-500
            Color(0xFFFB7185)  // rose-400
        )
    )
)

/**
 * Features displayed on the Features step (Step 2).
 * Four cards in a 2x2 grid showing app capabilities.
 */
val gridFeatures = listOf(
    OnboardingFeature(
        iconType = OnboardingIcon.TRENDING_UP,
        title = "Real-Time Prices",
        description = "Live updates every second",
        gradientColors = listOf(
            Color(0xFF2563EB), // blue-600
            Color(0xFF9333EA)  // purple-600
        )
    ),
    OnboardingFeature(
        iconType = OnboardingIcon.NOTIFICATIONS,
        title = "Smart Alerts",
        description = "Custom price notifications",
        gradientColors = listOf(
            Color(0xFF2563EB), // blue-600
            Color(0xFF9333EA)  // purple-600
        )
    ),
    OnboardingFeature(
        iconType = OnboardingIcon.WALLET,
        title = "Portfolio Tracking",
        description = "Monitor your investments",
        gradientColors = listOf(
            Color(0xFF2563EB), // blue-600
            Color(0xFF9333EA)  // purple-600
        )
    ),
    OnboardingFeature(
        iconType = OnboardingIcon.SHIELD,
        title = "Secure & Private",
        description = "Your data stays yours",
        gradientColors = listOf(
            Color(0xFF2563EB), // blue-600
            Color(0xFF9333EA)  // purple-600
        )
    )
)

/**
 * Represents a notification type shown on the Notifications step (Step 4).
 */
data class NotificationType(
    val iconType: OnboardingIcon,
    val title: String,
    val description: String,
    val statusText: String,
    val gradientColors: List<Color>
)

/**
 * Notification types displayed on the Notifications step.
 * Three info cards describing different notification categories.
 */
val notificationTypes = listOf(
    NotificationType(
        iconType = OnboardingIcon.TRENDING_UP,
        title = "Price Movement Alerts",
        description = "Get notified when prices hit your targets",
        statusText = "Real-time notifications",
        gradientColors = listOf(
            Color(0xFF34D399), // emerald-400
            Color(0xFF14B8A6)  // teal-500
        )
    ),
    NotificationType(
        iconType = OnboardingIcon.WALLET,
        title = "Portfolio Updates",
        description = "Daily summaries of your portfolio performance",
        statusText = "Daily at 9:00 AM",
        gradientColors = listOf(
            Color(0xFF3B82F6), // blue-500
            Color(0xFFA855F7)  // purple-500
        )
    ),
    NotificationType(
        iconType = OnboardingIcon.SPARKLES,
        title = "Market Insights",
        description = "Important news and market movements",
        statusText = "As they happen",
        gradientColors = listOf(
            Color(0xFFEC4899), // pink-500
            Color(0xFFFB7185)  // rose-400
        )
    )
)
