package com.blockmaster.game

import com.blockmaster.game.domain.Board
import com.blockmaster.game.domain.CollisionDetector
import com.blockmaster.game.model.PieceInstance
import com.blockmaster.game.model.TetrominoType
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.Test

class CollisionDetectorTest {
    @Test
    fun testCollisionWithWall() {
        val board = Board(10,20)
        val piece = PieceInstance(TetrominoType.I, -2, 0, 0)
        assertTrue(CollisionDetector.collides(board, piece))
    }

    @Test
    fun testNoCollisionEmptyBoard() {
        val board = Board(10,20)
        val piece = PieceInstance(TetrominoType.O, 4, 0, 0)
        assertFalse(CollisionDetector.collides(board, piece))
    }
}
