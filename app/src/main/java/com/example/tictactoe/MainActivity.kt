package com.example.tictactoe

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : Activity() {

    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameBoard = findViewById<GridLayout>(R.id.gameBoard)
        val statusMessage = findViewById<TextView>(R.id.statusMessage)
        val playAgainButton = findViewById<Button>(R.id.playAgainButton)

        for (i in 0 until gameBoard.childCount) {
            val cell = gameBoard.getChildAt(i) as Button
            val row = i / 3
            val col = i % 3

            cell.setOnClickListener {
                if (gameActive && cell.text.isEmpty()) {
                    cell.text = currentPlayer
                    board[row][col] = currentPlayer
                    if (checkWin()) {
                        statusMessage.text = "Player $currentPlayer Wins"
                        gameActive = false
                        playAgainButton.visibility = View.VISIBLE
                    } else if (isBoardFull()) {
                        statusMessage.text = "It's a Draw"
                        gameActive = false
                        playAgainButton.visibility = View.VISIBLE
                    } else {
                        currentPlayer = if (currentPlayer == "X") "O" else "X"
                        statusMessage.text = "Player $currentPlayer's Turn"
                    }
                }
            }
        }

        playAgainButton.setOnClickListener {
            resetGame(gameBoard, statusMessage, playAgainButton)
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][0] == board[i][2]) return true
            if (board[0][i] != null && board[0][i] == board[1][i] && board[0][i] == board[2][i]) return true
        }
        return (board[0][0] != null && board[0][0] == board[1][1] && board[0][0] == board[2][2]) ||
                (board[0][2] != null && board[0][2] == board[1][1] && board[0][2] == board[2][0])
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            for (cell in row) {
                if (cell == null) return false
            }
        }
        return true
    }

    private fun resetGame(gameBoard: GridLayout, statusMessage: TextView, playAgainButton: Button) {
        currentPlayer = "X"
        gameActive = true
        for (i in board.indices) {
            board[i].fill(null)
        }
        statusMessage.text = "Player X's Turn"
        playAgainButton.visibility = View.GONE

        for (i in 0 until gameBoard.childCount) {
            val cell = gameBoard.getChildAt(i) as Button
            cell.text = ""
        }
    }
}
