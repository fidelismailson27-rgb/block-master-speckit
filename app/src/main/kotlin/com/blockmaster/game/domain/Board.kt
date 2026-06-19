package com.blockmaster.game.domain

import com.blockmaster.game.model.TetrominoType

class Board(val cols: Int = 10, val rows: Int = 20) {
    private val grid: Array<IntArray> = Array(rows) { IntArray(cols) { 0 } }

    fun get(x: Int, y: Int): Int = if (y in 0 until rows && x in 0 until cols) grid[y][x] else -1

    fun set(x: Int, y: Int, typeIndex: Int) {
        if (y in 0 until rows && x in 0 until cols) grid[y][x] = typeIndex
    }

    fun clearLine(y: Int) {
        if (y !in 0 until rows) return
        for (row in y downTo 1) {
            grid[row] = grid[row - 1].copyOf()
        }
        grid[0] = IntArray(cols) { 0 }
    }

    fun clearFullLines(): Int {
        var cleared = 0
        var y = rows - 1
        while (y >= 0) {
            if (isLineFull(y)) {
                clearLine(y)
                cleared++
                // stay on same y because rows have shifted down
            } else {
                y--
            }
        }
        return cleared
    }

    private fun isLineFull(y: Int): Boolean {
        for (x in 0 until cols) if (grid[y][x] == 0) return false
        return true
    }

    fun toArray(): Array<IntArray> = Array(rows) { r -> grid[r].copyOf() }

    fun clearAll() {
        for (r in 0 until rows) {
            for (c in 0 until cols) grid[r][c] = 0
        }
    }
}
