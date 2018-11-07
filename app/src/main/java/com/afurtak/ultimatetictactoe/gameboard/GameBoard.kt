package com.afurtak.ultimatetictactoe.gameboard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout

class GameBoard : GridLayout {

    val depth: Int
    var childs: Array<GameBoard>? = null
    var fields: Array<TicTacToeField>? = null
    var parent: GameBoard = this
    var coordinates: Array<Int> = arrayOf()

    private constructor(context: Context, depth: Int, parent: GameBoard, coordinates: Array<Int>) : super(context) {
        this.coordinates = coordinates
        this.depth = depth
        this.parent = parent
        createBoard()
        println(depth)
    }

    constructor(context: Context, depth: Int) : super(context) {
        parent = this
        this.depth = depth
        createBoard()
        println(depth)
    }

    constructor(context: Context) : this(context, 0)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.depth = 0
        createBoard()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.depth = 0
        createBoard()
    }

    init {
        rowCount = 3
        columnCount = 3
    }

    private fun createBoard() {
        if (depth == 0) {
            fields = Array(9) {
                val button = TicTacToeField(context, this, coordinates.copyAndAppend(arrayOf(it)))
                button.addGridLayoutUndefinedSpecLayoutParams()
                addView(button)
                button
            }
        } else {
            childs = Array(9) {
                val child = GameBoard(context, depth - 1, this, coordinates.copyAndAppend(arrayOf(it)))
                child.addGridLayoutUndefinedSpecLayoutParams()
                addView(child)
                child
            }
        }
    }

    fun activate() {
        if (childs != null) {
            for (child in childs!!)
                child.activate()
        }
        else {
            for (field in fields!!) {
                field.activate()
            }
        }
    }

    fun deactivate() {
        if (childs != null) {
            for (child in childs!!)
                child.deactivate()
        }
        else {
            for (field in fields!!) {
                field.deactivate()
            }
        }
    }

    fun getRoot(): GameBoard {
        return if (parent.depth == this.depth)
            this
        else
            parent.getRoot()
    }
}

fun View.addGridLayoutUndefinedSpecLayoutParams() {
    val params = GridLayout.LayoutParams()
    params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f)
    params.width = 0
    params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f)
    params.height = 0
    this.layoutParams = params
}

fun Array<Int>.copy(): Array<Int> {
    val copy = Array(this.size) {
        this[it]
    }
    return copy
}

fun Array<Int>.copyAndAppend(toAppend: Array<Int>) : Array<Int> {
    return Array(size + toAppend.size) {
        when {
            it < size -> this[it]
            else -> toAppend[it - size]
        }
    }
}