package com.blockmaster.game.domain

import com.blockmaster.game.model.TetrominoType

/**
 * Simplified rotation system: provides block offsets for each tetromino per rotation state (0..3).
 * This is a compact, testable representation. Wall-kicks are provided as candidate offsets.
 */
object RotationSystem {
    // For simplicity, represent each piece as list of (x,y) offsets per rotation
    private val shapes: Map<TetrominoType, Array<Array<Pair<Int,Int>>>> = mapOf(
        TetrominoType.O to arrayOf(
            arrayOf(Pair(0,0), Pair(1,0), Pair(0,1), Pair(1,1)),
            arrayOf(Pair(0,0), Pair(1,0), Pair(0,1), Pair(1,1)),
            arrayOf(Pair(0,0), Pair(1,0), Pair(0,1), Pair(1,1)),
            arrayOf(Pair(0,0), Pair(1,0), Pair(0,1), Pair(1,1))
        ),
        // T as example (centered)
        TetrominoType.T to arrayOf(
            arrayOf(Pair(0,0), Pair(-1,0), Pair(1,0), Pair(0,1)),
            arrayOf(Pair(0,0), Pair(0,-1), Pair(0,1), Pair(1,0)),
            arrayOf(Pair(0,0), Pair(-1,0), Pair(1,0), Pair(0,-1)),
            arrayOf(Pair(0,0), Pair(0,-1), Pair(0,1), Pair(-1,0))
        ),
        // Basic approximations for other pieces
        TetrominoType.I to arrayOf(
            arrayOf(Pair(-1,0), Pair(0,0), Pair(1,0), Pair(2,0)),
            arrayOf(Pair(1,-1), Pair(1,0), Pair(1,1), Pair(1,2)),
            arrayOf(Pair(-1,1), Pair(0,1), Pair(1,1), Pair(2,1)),
            arrayOf(Pair(0,-1), Pair(0,0), Pair(0,1), Pair(0,2))
        ),
        TetrominoType.S to arrayOf(
            arrayOf(Pair(0,0), Pair(1,0), Pair(0,1), Pair(-1,1)),
            arrayOf(Pair(0,0), Pair(0,1), Pair(1,0), Pair(1,-1)),
            arrayOf(Pair(0,0), Pair(1,0), Pair(0,1), Pair(-1,1)),
            arrayOf(Pair(0,0), Pair(0,1), Pair(1,0), Pair(1,-1))
        ),
        TetrominoType.Z to arrayOf(
            arrayOf(Pair(0,0), Pair(-1,0), Pair(0,1), Pair(1,1)),
            arrayOf(Pair(0,0), Pair(0,1), Pair(-1,0), Pair(-1,-1)),
            arrayOf(Pair(0,0), Pair(-1,0), Pair(0,1), Pair(1,1)),
            arrayOf(Pair(0,0), Pair(0,1), Pair(-1,0), Pair(-1,-1))
        ),
        TetrominoType.J to arrayOf(
            arrayOf(Pair(0,0), Pair(-1,0), Pair(1,0), Pair(-1,1)),
            arrayOf(Pair(0,0), Pair(0,-1), Pair(0,1), Pair(1,1)),
            arrayOf(Pair(0,0), Pair(-1,0), Pair(1,0), Pair(1,-1)),
            arrayOf(Pair(0,0), Pair(0,-1), Pair(0,1), Pair(-1,-1))
        ),
        TetrominoType.L to arrayOf(
            arrayOf(Pair(0,0), Pair(-1,0), Pair(1,0), Pair(1,1)),
            arrayOf(Pair(0,0), Pair(0,-1), Pair(0,1), Pair(1,-1)),
            arrayOf(Pair(0,0), Pair(-1,0), Pair(1,0), Pair(-1,-1)),
            arrayOf(Pair(0,0), Pair(0,-1), Pair(0,1), Pair(-1,1))
        )
    )

    // Simplified wall-kick offsets to try when rotation collides
    val wallKicks: Array<Pair<Int,Int>> = arrayOf(
        Pair(0,0), Pair(1,0), Pair(-1,0), Pair(0,-1), Pair(0,1)
    )

    fun getOffsets(type: TetrominoType, rotation: Int): Array<Pair<Int,Int>> {
        val r = ((rotation % 4) + 4) % 4
        return shapes[type]?.get(r) ?: shapes[TetrominoType.T]!![r]
    }
}
