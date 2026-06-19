package com.blockmaster.game.domain

import com.blockmaster.game.model.GameState
import com.blockmaster.game.model.PieceInstance
import com.blockmaster.game.model.TetrominoType
import com.blockmaster.game.model.emptyBoard

class GameEngine(private val cols: Int = 10, private val rows: Int = 20) {
    private val board = Board(cols, rows)
    private val generator = PieceGenerator()
    private var current: PieceInstance? = null
    private var next: TetrominoType = generator.next()
    var score: Int = 0
    var level: Int = 0
    var lines: Int = 0
    var isGameOver: Boolean = false

    fun start() {
        spawnNext()
    }

    fun startAndGetState(): GameState {
        start()
        return toState()
    }

    fun getState(): GameState = toState()

    private fun spawnNext() {
        val t = next
        next = generator.next()
        // spawn position roughly centered top
        val spawnX = cols / 2
        val spawnY = 0
        val p = PieceInstance(t, spawnX, spawnY, 0)
        current = p
        if (CollisionDetector.collides(board, p)) {
            isGameOver = true
        }
    }

    fun stepGravity(): GameState {
        if (isGameOver) return toState()
        val cur = current ?: run { spawnNext(); current!! }
        val moved = cur.copy(y = cur.y + 1)
        if (!CollisionDetector.collides(board, moved)) {
            current = moved
        } else {
            // lock
            lockPiece(cur)
            val cleared = board.clearFullLines()
            lines += cleared
            // simplistic scoring
            if (cleared > 0) score += when (cleared) {
                1 -> 100
                2 -> 300
                3 -> 500
                4 -> 800
                else -> cleared * 200
            }
            spawnNext()
        }
        return toState()
    }

    fun applyCommand(cmd: Command): GameState {
        if (isGameOver) return toState()
        val cur = current ?: run { spawnNext(); current!! }
        when (cmd) {
            Command.Left -> attemptMove(cur.copy(x = cur.x - 1))
            Command.Right -> attemptMove(cur.copy(x = cur.x + 1))
            Command.Rotate -> attemptRotate(cur)
            Command.SoftDrop -> attemptMove(cur.copy(y = cur.y + 1))
            Command.HardDrop -> {
                var p = cur
                var dropped = 0
                while (!CollisionDetector.collides(board, p.copy(y = p.y + 1))) {
                    p = p.copy(y = p.y + 1)
                    dropped++
                }
                current = p
                lockPiece(p)
                val cleared = board.clearFullLines()
                lines += cleared
                if (cleared > 0) score += when (cleared) {
                    1 -> 100
                    2 -> 300
                    3 -> 500
                    4 -> 800
                    else -> cleared * 200
                }
                spawnNext()
            }
        }
        return toState()
    }

    private fun attemptMove(candidate: PieceInstance) {
        if (!CollisionDetector.collides(board, candidate)) current = candidate
    }

    private fun attemptRotate(cur: PieceInstance) {
        val newRot = (cur.rotation + 1) % 4
        val candidate = cur.copy(rotation = newRot)
        if (!CollisionDetector.collides(board, candidate)) {
            current = candidate
            return
        }
        // try wall kicks
        for (kick in RotationSystem.wallKicks) {
            val kicked = candidate.copy(x = cur.x + kick.first, y = cur.y + kick.second)
            if (!CollisionDetector.collides(board, kicked)) {
                current = kicked
                return
            }
        }
    }

    private fun lockPiece(cur: PieceInstance) {
        val offsets = RotationSystem.getOffsets(cur.type, cur.rotation)
        for ((dx, dy) in offsets) {
            val x = cur.x + dx
            val y = cur.y + dy
            if (y in 0 until rows && x in 0 until cols) board.set(x, y, cur.type.ordinal + 1)
        }
    }

    private fun toState(): GameState {
        val b = board.toArray()
        return GameState(b, current, next, score, level, lines, false, isGameOver)
    }

    enum class Command { Left, Right, Rotate, SoftDrop, HardDrop }
}
