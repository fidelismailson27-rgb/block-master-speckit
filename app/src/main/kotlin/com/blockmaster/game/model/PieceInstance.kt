package com.blockmaster.game.model

import com.blockmaster.game.model.TetrominoType

data class PieceInstance(
    val type: TetrominoType,
    val x: Int,
    val y: Int,
    val rotation: Int // 0..3
)
