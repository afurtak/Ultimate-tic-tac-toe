package com.afurtak.ultimatetictactoe.gameboard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout

class GameBoard : GridLayout {

    companion object {
        var currentPlayer: BoardState = BoardState.Cross

        fun changePlayer() {
            currentPlayer = if (currentPlayer == BoardState.Cross)
                BoardState.Circle
            else
                BoardState.Cross
        }
    }

    val depth: Int
    var children: Array<GameBoard>? = null
    var fields: Array<TicTacToeField>? = null
    var parent: GameBoard = this
    var coordinates: Array<Int> = arrayOf()
    var state = BoardState.Empty

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
                val button = TicTacToeField(context, this, coordinates.copyAndAppend(arrayOf(it))).apply {
                    addGridLayoutUndefinedSpecLayoutParams()
                    setOnClickListener {
                        this.setAsClicked(GameBoard.currentPlayer)
                        this@GameBoard.updateBoardRecursive()
                    }
                }
                addView(button)
                button
            }
        } else {
            children = Array(9) {
                val child = GameBoard(context, depth - 1, this, coordinates.copyAndAppend(arrayOf(it)))
                child.addGridLayoutUndefinedSpecLayoutParams()
                addView(child)
                child
            }
        }
    }

    fun activate() {
        if (children != null) {
            for (child in children!!)
                child.activate()
        }
        else {
            for (field in fields!!) {
                field.activate()
            }
        }
    }

    fun deactivate() {
        if (children != null) {
            for (child in children!!)
                child.deactivate()
        }
        else {
            for (field in fields!!) {
                field.deactivate()
            }
        }
    }

    /**
     * Check if any of players has 3 signs in one row, col or on diagonal,
     * and if any of them does, change state of board.
     *
     * @return false if state of the board was not changed and true otherwise
     */
    fun updateState(): Boolean {
        if (state != BoardState.Empty)
            return false

        val fieldsValues: Array<Int>
        if (depth == 0) {
            fieldsValues = Array(9) {
                when (fields!![it].state) {
                    BoardState.Cross -> 1
                    BoardState.Circle -> 17
                    else -> 0
                }
            }
        }
        else {
            fieldsValues = Array(9) {
                when (children!![it].state) {
                    BoardState.Cross -> 1
                    BoardState.Circle -> 7
                    else -> 0
                }
            }
        }

        val value = arrayOf(
                fieldsValues[0] + fieldsValues[1] + fieldsValues[2],
                fieldsValues[3] + fieldsValues[4] + fieldsValues[5],
                fieldsValues[6] + fieldsValues[7] + fieldsValues[8],
                fieldsValues[0] + fieldsValues[3] + fieldsValues[6],
                fieldsValues[1] + fieldsValues[4] + fieldsValues[7],
                fieldsValues[2] + fieldsValues[5] + fieldsValues[8],
                fieldsValues[0] + fieldsValues[4] + fieldsValues[8],
                fieldsValues[2] + fieldsValues[4] + fieldsValues[6]
        )

        for (v in value) {
            if (v == 3) {
                state = BoardState.Cross
                return true
            }
            else if (v == 21) {
                state = BoardState.Circle
                return true
            }
        }
        return false
    }

    /**
     * Updates board recursive up to the root.
     * If state changes in root, will call endOfTheGame method which should finish game.
     */
    fun updateBoardRecursive() {
        if (isRoot()) {
            if (updateState())
                endOfTheGame()
        }
        else if (updateState())
            parent.updateState()
    }

    fun getRoot(): GameBoard {
        return if (parent.depth == this.depth)
            this
        else
            parent.getRoot()
    }

    fun isRoot(): Boolean {
        return parent.depth == this.depth
    }

    fun getViewByCoordinates(coordinates: Array<Int>, currentCoordinate: Int = 0): View {
        if (currentCoordinate == 0 && !isRoot())
            return getRoot().getViewByCoordinates(coordinates)

        return if (depth == 0)
            fields!![coordinates[currentCoordinate]]
        else
            children!![coordinates[currentCoordinate + 1]]
                    .getViewByCoordinates(coordinates, currentCoordinate + 1)
    }

    fun endOfTheGame() {

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
    return Array(size) {
        this[it]
    }
}

fun Array<Int>.copyAndAppend(toAppend: Array<Int>) : Array<Int> {
    return Array(size + toAppend.size) {
        when {
            it < size -> this[it]
            else -> toAppend[it - size]
        }
    }
}