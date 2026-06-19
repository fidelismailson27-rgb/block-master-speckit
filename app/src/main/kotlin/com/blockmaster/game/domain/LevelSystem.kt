package com.blockmaster.game.domain

object LevelSystem {
    fun levelForLines(lines: Int, perLevel: Int = 10): Int = lines / perLevel

    fun gravityMsForLevel(level: Int): Long {
        // simplified table
        return when (level) {
            0 -> 1000L
            1 -> 830L
            2 -> 716L
            else -> (1000L * Math.pow(0.85, level.toDouble())).toLong()
        }
    }
}
