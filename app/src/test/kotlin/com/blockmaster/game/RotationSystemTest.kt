package com.blockmaster.game

import com.blockmaster.game.domain.RotationSystem
import com.blockmaster.game.model.TetrominoType
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test

class RotationSystemTest {
    @Test
    fun testOStaysSameAfterRotation() {
        val offs0 = RotationSystem.getOffsets(TetrominoType.O, 0)
        val offs1 = RotationSystem.getOffsets(TetrominoType.O, 1)
        assertEquals(offs0.size, offs1.size)
        assertTrue(offs0.contentEquals(offs1))
    }

    @Test
    fun testFourRotationsReturnSameCount() {
        for (t in TetrominoType.values()) {
            val sizes = (0..3).map { RotationSystem.getOffsets(t,it).size }
            assertTrue(sizes.all { it==sizes[0] })
        }
    }
}
