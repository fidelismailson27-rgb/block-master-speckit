package com.blockmaster.game

import com.blockmaster.game.domain.PieceGenerator
import com.blockmaster.game.model.TetrominoType
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test
import java.util.Random

class PieceGeneratorTest {
    @Test
    fun testBagContainsAllTypesBeforeRefill() {
        val gen = PieceGenerator(kotlin.random.Random(42))
        val seen = mutableSetOf<TetrominoType>()
        repeat(7) { seen.add(gen.next()) }
        assertEquals(7, seen.size)
    }
}
