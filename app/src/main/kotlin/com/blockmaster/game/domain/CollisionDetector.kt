package com.blockmaster.game.domain

import com.blockmaster.game.model.PieceInstance
import com.blockmaster.game.domain.RotationSystem

object CollisionDetector {
    fun collides(board: Board, piece: PieceInstance): Boolean {
        val offsets = RotationSystem.getOffsets(piece.type, piece.rotation)
        for ((dx, dy) in offsets) {
            val x = piece.x + dx
            val y = piece.y + dy
            // out of bounds
            if (x < 0 || x >= board.cols || y < 0 || y >= board.rows) return true
            if (board.get(x,y) != 0) return true
        }
        return false
    }
}
