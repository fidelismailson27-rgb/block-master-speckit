package com.blockmaster.game

import com.blockmaster.game.domain.ScoringSystem
import com.blockmaster.game.domain.LevelSystem
import kotlin.test.assertEquals
import org.junit.Test

class ScoringLevelTest {
    @Test
    fun testScoringMultipliers() {
        assertEquals(100, ScoringSystem.scoreForClear(1,0))
        assertEquals(200, ScoringSystem.scoreForClear(1,1))
    }

    @Test
    fun testLevelForLines() {
        assertEquals(0, LevelSystem.levelForLines(0))
        assertEquals(1, LevelSystem.levelForLines(10))
    }
}
