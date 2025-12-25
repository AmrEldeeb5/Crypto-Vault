package com.example.cryptowallet.app.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptowallet.app.onboarding.data.OnboardingRepository
import com.example.cryptowallet.app.onboarding.domain.OnboardingStep
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the onboarding flow.
 * Manages state and handles events for the 4-step onboarding process.
 */
class OnboardingViewModel(
    private val onboardingRepository: OnboardingRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()
    
    /**
     * Whether the user can proceed to the next step.
     * Returns false on step 2 (Coin Selection) if no coins are selected.
     */
    val canProceed: StateFlow<Boolean> = _state.map { state ->
        when (state.currentStep) {
            2 -> state.selectedCoins.isNotEmpty()
            else -> true
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    
    /**
     * The current step's gradient colors for UI styling.
     */
    val currentStepColors: StateFlow<List<androidx.compose.ui.graphics.Color>> = _state.map { state ->
        OnboardingStep.fromIndex(state.currentStep).gradientColors
    }.stateIn(
        viewModelScope, 
        SharingStarted.WhileSubscribed(5000), 
        OnboardingStep.Welcome.gradientColors
    )
    
    // Navigation callback to be set by the screen
    private var onNavigateToMain: (() -> Unit)? = null
    
    init {
        loadSavedState()
    }
    
    /**
     * Set the navigation callback for completing onboarding.
     */
    fun setNavigationCallback(callback: () -> Unit) {
        onNavigateToMain = callback
    }
    
    /**
     * Load saved onboarding state from DataStore.
     */
    private fun loadSavedState() {
        viewModelScope.launch {
            try {
                val savedState = onboardingRepository.getOnboardingState()
                _state.update { savedState.copy(isLoading = false) }
            } catch (e: Exception) {
                // If loading fails, start fresh
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
    
    /**
     * Handle all onboarding events.
     */
    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.NextStep -> handleNextStep()
            is OnboardingEvent.PreviousStep -> handlePreviousStep()
            is OnboardingEvent.SkipToEnd -> showSkipConfirmation()
            is OnboardingEvent.ConfirmSkip -> confirmSkip()
            is OnboardingEvent.DismissSkipDialog -> dismissSkipDialog()
            is OnboardingEvent.ToggleCoin -> toggleCoin(event.coinSymbol)
            is OnboardingEvent.ToggleNotifications -> toggleNotifications()
            is OnboardingEvent.CompleteOnboarding -> completeOnboarding()
            is OnboardingEvent.NavigateToMain -> navigateToMain()
        }
    }
    
    /**
     * Navigate to the next step with transition animation.
     */
    private fun handleNextStep() {
        val currentState = _state.value
        if (currentState.currentStep < 3 && !currentState.isTransitioning) {
            // Check if can proceed (coin selection validation)
            if (currentState.currentStep == 2 && currentState.selectedCoins.isEmpty()) {
                return
            }
            
            _state.update { it.copy(isTransitioning = true) }
            viewModelScope.launch {
                delay(TRANSITION_DURATION_MS)
                _state.update { 
                    it.copy(
                        currentStep = it.currentStep + 1,
                        isTransitioning = false
                    )
                }
                saveState()
            }
        } else if (currentState.currentStep == 3) {
            completeOnboarding()
        }
    }
    
    /**
     * Navigate to the previous step with transition animation.
     */
    private fun handlePreviousStep() {
        val currentState = _state.value
        if (currentState.currentStep > 0 && !currentState.isTransitioning) {
            _state.update { it.copy(isTransitioning = true) }
            viewModelScope.launch {
                delay(TRANSITION_DURATION_MS)
                _state.update { 
                    it.copy(
                        currentStep = it.currentStep - 1,
                        isTransitioning = false
                    )
                }
                saveState()
            }
        }
    }
    
    /**
     * Show the skip confirmation dialog.
     */
    private fun showSkipConfirmation() {
        _state.update { it.copy(showSkipDialog = true) }
    }
    
    /**
     * Confirm skipping to the final step.
     */
    private fun confirmSkip() {
        _state.update { 
            it.copy(
                showSkipDialog = false,
                isTransitioning = true
            )
        }
        viewModelScope.launch {
            delay(TRANSITION_DURATION_MS)
            _state.update { 
                it.copy(
                    currentStep = 3,
                    isTransitioning = false
                )
            }
            saveState()
        }
    }
    
    /**
     * Dismiss the skip confirmation dialog.
     */
    private fun dismissSkipDialog() {
        _state.update { it.copy(showSkipDialog = false) }
    }
    
    /**
     * Toggle a coin's selection state.
     */
    private fun toggleCoin(coinSymbol: String) {
        _state.update { state ->
            val newSelectedCoins = if (state.selectedCoins.contains(coinSymbol)) {
                state.selectedCoins - coinSymbol
            } else {
                state.selectedCoins + coinSymbol
            }
            state.copy(selectedCoins = newSelectedCoins)
        }
        viewModelScope.launch {
            saveState()
        }
    }
    
    /**
     * Toggle notification preference.
     */
    private fun toggleNotifications() {
        _state.update { it.copy(notificationsEnabled = !it.notificationsEnabled) }
        viewModelScope.launch {
            saveState()
        }
    }
    
    /**
     * Complete the onboarding flow.
     * Shows success animation, saves coins to watchlist, and marks onboarding complete.
     */
    private fun completeOnboarding() {
        _state.update { it.copy(showSuccessAnimation = true) }
        viewModelScope.launch {
            try {
                // Save selected coins to watchlist
                onboardingRepository.saveSelectedCoinsToWatchlist(_state.value.selectedCoins)
                // Mark onboarding as completed
                onboardingRepository.setOnboardingCompleted(true)
            } catch (e: Exception) {
                // Continue even if saving fails
            }
            
            // Wait for success animation
            delay(SUCCESS_ANIMATION_DURATION_MS)
            
            // Navigate to main screen
            navigateToMain()
        }
    }
    
    /**
     * Navigate to the main screen.
     */
    private fun navigateToMain() {
        onNavigateToMain?.invoke()
    }
    
    /**
     * Save current state to DataStore.
     */
    private suspend fun saveState() {
        try {
            onboardingRepository.saveOnboardingState(_state.value)
        } catch (e: Exception) {
            // Non-blocking - continue even if save fails
        }
    }
    
    companion object {
        private const val TRANSITION_DURATION_MS = 300L
        private const val SUCCESS_ANIMATION_DURATION_MS = 1500L
    }
}
