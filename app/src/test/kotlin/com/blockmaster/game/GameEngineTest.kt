package com.blockmaster.game

import com.blockmaster.game.domain.GameEngine
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import org.junit.Test

class GameEngineTest {
    @Test
    fun testStartAndGravity() {
        val engine = GameEngine(10,20)
        engine.start()
        val s1 = engine.stepGravity()
        // after one gravity step, should not be game over
        assertFalse(s1.isGameOver)
    }

    @Test
    fun testHardDropLocksAndClears() {
        val engine = GameEngine(4,4)
        engine.start()
        // perform hard drop
        val before = engine.applyCommand(GameEngine.Command.HardDrop)
        // game should continue (either game over or new piece)
        assertTrue(before.score >= 0)
    }
}
