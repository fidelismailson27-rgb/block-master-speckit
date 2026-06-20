package com.blockmaster.game.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blockmaster.game.R
import com.blockmaster.game.viewmodel.GameViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel = GameViewModel()
    private lateinit var boardGrid: GridLayout
    private val cellViews = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boardGrid = findViewById(R.id.boardGrid)
        val scoreText: TextView = findViewById(R.id.scoreText)
        val levelText: TextView = findViewById(R.id.levelText)
        val linesText: TextView = findViewById(R.id.linesText)
        val nextPieceText: TextView = findViewById(R.id.nextPieceText)

        val dpadUp: Button = findViewById(R.id.dpad_up)
        val dpadLeft: Button = findViewById(R.id.dpad_left)
        val dpadRight: Button = findViewById(R.id.dpad_right)
        val dpadDown: Button = findViewById(R.id.dpad_down)
        val rotateBtn: Button = findViewById(R.id.btn_rotate)
        val hardDropBtn: Button = findViewById(R.id.btn_harddrop)
        val pauseButton: Button = findViewById(R.id.pauseButton)
        val restartButton: Button = findViewById(R.id.restartButton)

        // initialize grid cells
        initGrid()

        dpadLeft.setOnClickListener { viewModel.moveLeft() }
        dpadRight.setOnClickListener { viewModel.moveRight() }
        dpadDown.setOnClickListener { viewModel.softDrop() }
        dpadUp.setOnClickListener { viewModel.rotateClockwise() }
        rotateBtn.setOnClickListener { viewModel.rotateClockwise() }
        hardDropBtn.setOnClickListener { viewModel.hardDrop() }
        pauseButton.setOnClickListener { viewModel.pauseToggle() }
        restartButton.setOnClickListener { viewModel.restart() }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                // update HUD
                scoreText.text = "Score: ${state.score}"
                levelText.text = "Level: ${state.level}"
                linesText.text = "Lines: ${state.lines}"
                nextPieceText.text = "Next: ${state.next?.name ?: "-"}"

                updateBoard(state)
            }
        }

        // ensure game starts when activity becomes visible
        viewModel.start()
    }

    private fun initGrid() {
        val cols = 10
        val rows = 20
        boardGrid.columnCount = cols
        boardGrid.rowCount = rows
        val size = (resources.displayMetrics.density * 18).toInt()
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val v = View(this)
                val lp = GridLayout.LayoutParams()
                lp.width = size
                lp.height = size
                lp.setMargins(1,1,1,1)
                v.layoutParams = lp
                v.setBackgroundColor(Color.DKGRAY)
                boardGrid.addView(v)
                cellViews.add(v)
            }
        }
    }

    private fun updateBoard(state: com.blockmaster.game.model.GameState) {
        val board = state.board
        // board is rows x cols
        val rows = board.size
        val cols = if (rows>0) board[0].size else 0
        // First draw locked cells
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val idx = r*cols + c
                val cell = board[r][c]
                val view = cellViews.getOrNull(idx) ?: continue
                if (cell == 0) view.setBackgroundColor(Color.BLACK)
                else view.setBackgroundColor(colorFor(cell))
            }
        }
        // Then overlay current falling piece
        val cur = state.current
        if (cur != null) {
            // get offsets for piece
            val offsets = com.blockmaster.game.domain.RotationSystem.getOffsets(cur.type, cur.rotation)
            for ((dx, dy) in offsets) {
                val x = cur.x + dx
                val y = cur.y + dy
                if (y in 0 until rows && x in 0 until cols) {
                    val idx = y*cols + x
                    val v = cellViews.getOrNull(idx) ?: continue
                    // current piece uses type index = ordinal+1
                    v.setBackgroundColor(colorFor(cur.type.ordinal + 1))
                }
            }
        }
    }

    private fun colorFor(typeIndex: Int): Int {
        return when ((typeIndex-1) % 7) {
            0 -> Color.CYAN // I
            1 -> Color.YELLOW // O
            2 -> Color.MAGENTA // T
            3 -> Color.GREEN // S
            4 -> Color.RED // Z
            5 -> Color.BLUE // J
            6 -> Color.rgb(255,165,0) // L orange
            else -> Color.WHITE
        }
    }
}
