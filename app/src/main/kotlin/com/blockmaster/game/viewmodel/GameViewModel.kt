package com.blockmaster.game.viewmodel

import com.blockmaster.game.domain.GameEngine
import com.blockmaster.game.domain.LevelSystem
import com.blockmaster.game.model.GameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val engine: GameEngine = GameEngine()) {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val _uiState = MutableStateFlow(engine.getState())
    val uiState: StateFlow<GameState> = _uiState

    private var loopJob: Job? = null
    @Volatile private var paused: Boolean = false

    fun start() {
        if (loopJob?.isActive == true) return
        loopJob = scope.launch {
            engine.start()
            _uiState.value = engine.getState()
            while (!engine.isGameOver) {
                if (paused) {
                    delay(100L)
                    continue
                }
                _uiState.value = engine.stepGravity()
                val delayMs = LevelSystem.gravityMsForLevel(engine.level)
                delay(delayMs)
            }
            // final state
            _uiState.value = engine.getState()
        }
    }

    fun pauseToggle() {
        paused = !paused
    }

    fun restart() {
        scope.launch {
            loopJob?.cancel()
            engine.reset()
            _uiState.value = engine.startAndGetState()
            paused = false
            start()
        }
    }

    fun moveLeft() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.Left) } }
    fun moveRight() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.Right) } }
    fun rotateClockwise() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.Rotate) } }
    fun softDrop() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.SoftDrop) } }
    fun hardDrop() { scope.launch { _uiState.value = engine.applyCommand(GameEngine.Command.HardDrop) } }
}
