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
    var isWon = false
    var clickedState = BoardState.Empty
    var wonState = BoardState.Empty

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

    fun setAsNotClicked() {
        clickedState = BoardState.Empty
        text = ""
        isClicked = false
    }

    fun setAsWon(state: BoardState) {
        wonState = state
        text = if (state == BoardState.Cross)
            "X"
        else
            "O"
        isWon = true
    }

    fun undoSetAsWon() {
        isWon = false
        wonState = BoardState.Empty
        text = if (!isClicked)
            ""
        else
            if (clickedState == BoardState.Cross)
                "X"
            else
                "O"
    }

    private fun setAsCircle() {
        text = "O"
        clickedState = BoardState.Circle
    }

    private fun setAsCross() {
        text = "X"
        clickedState = BoardState.Cross
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