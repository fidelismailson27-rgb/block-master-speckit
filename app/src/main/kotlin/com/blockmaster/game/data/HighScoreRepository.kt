package com.blockmaster.game.data

import java.io.File

class HighScoreRepository(private val filePath: String) {
    private val file = File(filePath)

    fun saveHighScore(score: Int) {
        file.parentFile?.mkdirs()
        file.writeText(score.toString())
    }

    fun loadHighScore(): Int {
        return if (file.exists()) {
            file.readText().trim().toIntOrNull() ?: 0
        } else 0
    }
}
