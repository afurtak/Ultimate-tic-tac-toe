package com.afurtak.ultimatetictactoe.game

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.Button

class TicTacToeField(context: Context, val parent: GameBoard, val coordinates: Array<Int>, val it: Int) : Button(context) {

    companion object {
        val activeColor = Color.argb(0, 0, 0, 0)
        val notActiveColor = Color.argb(20, 0, 0, 0)
    }

    private constructor(context: Context, attrs: AttributeSet) : this(context, GameBoard(context), arrayOf(), 0)

    private constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, GameBoard(context), arrayOf(), 0)

    var isClicked = false
    var state = BoardState.Empty

    init {

    }

    fun setAsClicked(state: BoardState) {
        if (state == BoardState.Cross)
            setAsCross()
        else
            setAsCircle()

        deactivate()
        isClicked = true
    }

    private fun setAsCircle() {
        text = "O"
        state = BoardState.Circle
    }

    private fun setAsCross() {
        text = "X"
        state = BoardState.Cross
    }

    fun activate() {
        setBackgroundColor(activeColor)
        if (!isClicked)
            isClickable = true
    }

    fun deactivate() {
        setBackgroundColor(notActiveColor)
        isClickable = false
    }

    fun erase() {

    }
}