package com.blockmaster.game.viewmodel

import com.blockmaster.game.domain.GameEngine
import com.blockmaster.game.model.GameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val engine: GameEngine = GameEngine()) {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val _uiState = MutableStateFlow(engine.startAndGetState())
    val uiState: StateFlow<GameState> = _uiState

    fun start() {
        scope.launch {
            engine.start()
            _uiState.value = engine.stepGravity()
        }
    }

    fun pauseToggle() {
        // simple placeholder: not implemented
    }

    fun restart() {
        scope.launch {
            engine.reset()
            _uiState.value = engine.startAndGetState()
        }
    }

    fun moveLeft() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.Left) } }
    fun moveRight() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.Right) } }
    fun rotateClockwise() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.Rotate) } }
    fun softDrop() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.SoftDrop) } }
    fun hardDrop() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.HardDrop) } }
}
