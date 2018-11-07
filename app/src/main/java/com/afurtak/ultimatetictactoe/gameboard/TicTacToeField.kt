package com.afurtak.ultimatetictactoe.gameboard

import android.content.Context
import android.util.AttributeSet
import android.widget.Button

class TicTacToeField(context: Context, val parent: GameBoard, val coordinates: Array<Int>) : Button(context) {

    private constructor(context: Context, attrs: AttributeSet) : this(context, GameBoard(context), arrayOf())

    private constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, GameBoard(context), arrayOf())

    init {

    }

    fun activate() {
        isClickable = true
    }

    fun deactivate() {
        isClickable = false
    }

    fun erase() {

    }
}