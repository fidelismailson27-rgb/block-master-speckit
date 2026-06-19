package com.blockmaster.game

import com.blockmaster.game.domain.Board
import kotlin.test.assertEquals
import org.junit.Test

class BoardTest {
    @Test
    fun testClearFullLines() {
        val b = Board(4,4)
        // fill bottom row
        for (x in 0..3) b.set(x,3,1)
        val cleared = b.clearFullLines()
        assertEquals(1, cleared)
        // bottom row should now be empty
        for (x in 0..3) assertEquals(0, b.get(x,3))
    }
}
