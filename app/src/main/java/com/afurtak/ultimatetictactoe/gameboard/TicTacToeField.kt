package com.afurtak.ultimatetictactoe.gameboard

import android.content.Context
import android.util.AttributeSet
import android.widget.Button

class TicTacToeField(context: Context, val parent: GameBoard, val coordinates: Array<Int>) : Button(context) {

    private constructor(context: Context, attrs: AttributeSet) : this(context, GameBoard(context), arrayOf())

    private constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, GameBoard(context), arrayOf())

    var isClicked = false

    init {

    }

    fun setAsClicked(state: BoardState) {
        if (state == BoardState.Cross)
            setAsCross()
        else
            setAsCircle()

        GameBoard.changePlayer()
        deactivate()
        isClicked = true
    }

    private fun setAsCircle() {
        text = "O"
    }

    private fun setAsCross() {
        text = "X"
    }

    fun activate() {
        if (!isClicked)
            isClickable = true
    }

    fun deactivate() {
        if (!isClicked)
            isClickable = false
    }

    fun erase() {

    }
}