package com.blockmaster.game.ui

import com.blockmaster.game.viewmodel.GameViewModel

// Simple touch control mapping placeholder
class ControlsView(private val viewModel: GameViewModel) {
    fun onLeftPressed() { viewModel.moveLeft() }
    fun onRightPressed() { viewModel.moveRight() }
    fun onRotatePressed() { viewModel.rotateClockwise() }
    fun onSoftDropPressed() { viewModel.softDrop() }
    fun onHardDropPressed() { viewModel.hardDrop() }
    fun onPausePressed() { viewModel.pauseToggle() }
}
