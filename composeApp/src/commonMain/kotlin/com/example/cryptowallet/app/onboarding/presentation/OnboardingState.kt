package com.example.cryptowallet.app.onboarding.presentation

/**
 * Represents the UI state for the onboarding flow.
 * 
 * @property currentStep The current step index (0-3)
 * @property selectedCoins Set of coin symbols selected by the user
 * @property notificationsEnabled Whether the user has enabled notifications
 * @property isTransitioning Whether a step transition animation is in progress
 * @property showSkipDialog Whether to show the skip confirmation dialog
 * @property showSuccessAnimation Whether to show the completion success animation
 * @property isLoading Whether the initial state is being loaded from DataStore
 */
data class OnboardingState(
    val currentStep: Int = 0,
    val selectedCoins: Set<String> = emptySet(),
    val notificationsEnabled: Boolean = false,
    val isTransitioning: Boolean = false,
    val showSkipDialog: Boolean = false,
    val showSuccessAnimation: Boolean = false,
    val isLoading: Boolean = true
) {
    /**
     * Returns the progress percentage (25%, 50%, 75%, 100%)
     */
    val progressPercentage: Int
        get() = ((currentStep + 1) * 25)
    
    /**
     * Returns the step text (e.g., "Step 1 of 4")
     */
    val stepText: String
        get() = "Step ${currentStep + 1} of 4"
    
    /**
     * Returns the number of filled progress segments (1-4)
     */
    val filledSegments: Int
        get() = currentStep + 1
    
    /**
     * Returns whether the continue button should show "Get Started" text
     */
    val isLastStep: Boolean
        get() = currentStep == 3
    
    /**
     * Returns the button text based on current step
     */
    val buttonText: String
        get() = if (isLastStep) "Get Started" else "Continue"
    
    /**
     * Returns whether the back button should be visible
     */
    val showBackButton: Boolean
        get() = currentStep > 0
    
    /**
     * Returns whether the skip button should be visible
     */
    val showSkipButton: Boolean
        get() = currentStep < 3
    
    /**
     * Returns the count of selected coins for display
     */
    val selectedCoinsCount: Int
        get() = selectedCoins.size
    
    /**
     * Returns the selection badge text
     */
    val selectionBadgeText: String
        get() = "✓ $selectedCoinsCount ${if (selectedCoinsCount == 1) "coin" else "coins"} selected"
}

/**
 * Sealed class representing all possible events in the onboarding flow.
 */
sealed class OnboardingEvent {
    /**
     * Navigate to the next step
     */
    data object NextStep : OnboardingEvent()
    
    /**
     * Navigate to the previous step
     */
    data object PreviousStep : OnboardingEvent()
    
    /**
     * Show the skip confirmation dialog
     */
    data object SkipToEnd : OnboardingEvent()
    
    /**
     * Confirm skipping to the final step
     */
    data object ConfirmSkip : OnboardingEvent()
    
    /**
     * Dismiss the skip confirmation dialog
     */
    data object DismissSkipDialog : OnboardingEvent()
    
    /**
     * Toggle a coin's selection state
     */
    data class ToggleCoin(val coinSymbol: String) : OnboardingEvent()
    
    /**
     * Toggle notification preference
     */
    data object ToggleNotifications : OnboardingEvent()
    
    /**
     * Complete the onboarding flow
     */
    data object CompleteOnboarding : OnboardingEvent()
    
    /**
     * Navigate to main screen after success animation
     */
    data object NavigateToMain : OnboardingEvent()
}
