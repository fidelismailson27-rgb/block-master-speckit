package com.blockmaster.game.data

import java.nio.file.Files
import java.nio.file.Path

class HighScoreRepository(private val path: Path) {
    fun saveHighScore(score: Int) {
        Files.writeString(path, score.toString())
    }

    fun loadHighScore(): Int {
        return if (Files.exists(path)) {
            Files.readString(path).trim().toIntOrNull() ?: 0
        } else 0
    }
}
