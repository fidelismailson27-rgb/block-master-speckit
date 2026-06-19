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

        val leftButton: Button = findViewById(R.id.leftButton)
        val rightButton: Button = findViewById(R.id.rightButton)
        val rotateButton: Button = findViewById(R.id.rotateButton)
        val softDropButton: Button = findViewById(R.id.softDropButton)
        val hardDropButton: Button = findViewById(R.id.hardDropButton)
        val pauseButton: Button = findViewById(R.id.pauseButton)
        val restartButton: Button = findViewById(R.id.restartButton)

        // initialize grid cells
        initGrid()

        leftButton.setOnClickListener { viewModel.moveLeft() }
        rightButton.setOnClickListener { viewModel.moveRight() }
        rotateButton.setOnClickListener { viewModel.rotateClockwise() }
        softDropButton.setOnClickListener { viewModel.softDrop() }
        hardDropButton.setOnClickListener { viewModel.hardDrop() }
        pauseButton.setOnClickListener { viewModel.pauseToggle() }
        restartButton.setOnClickListener { viewModel.restart() }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                // update HUD
                scoreText.text = "Score: ${state.score}"
                levelText.text = "Level: ${state.level}"
                linesText.text = "Lines: ${state.lines}"
                nextPieceText.text = "Next: ${state.next?.name ?: "-"}"

                updateBoard(state.board)
            }
        }

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

    private fun updateBoard(board: Array<IntArray>) {
        // board is rows x cols
        val rows = board.size
        val cols = if (rows>0) board[0].size else 0
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val idx = r*cols + c
                val cell = board[r][c]
                val view = cellViews.getOrNull(idx) ?: continue
                if (cell == 0) view.setBackgroundColor(Color.BLACK)
                else view.setBackgroundColor(colorFor(cell))
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
