package com.blockmaster.game.model

data class GameState(
    val board: Array<IntArray>, // 0 empty, >0 type index
    val current: PieceInstance?,
    val next: TetrominoType?,
    val score: Int = 0,
    val level: Int = 0,
    val lines: Int = 0,
    val isPaused: Boolean = false,
    val isGameOver: Boolean = false
)

fun emptyBoard(cols: Int = 10, rows: Int = 20): Array<IntArray> = Array(rows) { IntArray(cols) { 0 } }
