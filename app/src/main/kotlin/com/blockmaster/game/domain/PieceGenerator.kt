package com.blockmaster.game.domain

import com.blockmaster.game.model.TetrominoType
import kotlin.random.Random

class PieceGenerator(private val random: Random = Random.Default) {
    private val bag = ArrayList<TetrominoType>()

    fun next(): TetrominoType {
        if (bag.isEmpty()) refill()
        return bag.removeAt(0)
    }

    private fun refill() {
        bag.clear()
        val values = TetrominoType.values().toMutableList()
        values.shuffle(random)
        bag.addAll(values)
    }
}
