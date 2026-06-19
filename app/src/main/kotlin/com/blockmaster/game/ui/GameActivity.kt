package com.blockmaster.game.ui

import com.blockmaster.game.viewmodel.GameViewModel

// Placeholder GameActivity/GameFragment skeleton. In a real Android app this would extend Fragment or ComponentActivity
class GameActivity {
    private val viewModel = GameViewModel()

    fun onCreate() {
        // bind UI to viewModel.uiState
        viewModel.start()
    }

    fun render() {
        // Render board and HUD based on viewModel.uiState (StateFlow) updates
    }
}
