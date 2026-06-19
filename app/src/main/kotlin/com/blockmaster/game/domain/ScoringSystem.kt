package com.blockmaster.game.domain

object ScoringSystem {
    fun scoreForClear(lines: Int, level: Int): Int {
        val base = when (lines) {
            1 -> 100
            2 -> 300
            3 -> 500
            4 -> 800
            else -> lines * 200
        }
        return base * (level + 1)
    }

    fun softDropPoints(cells: Int): Int = cells
    fun hardDropPoints(cells: Int): Int = cells * 2
}
