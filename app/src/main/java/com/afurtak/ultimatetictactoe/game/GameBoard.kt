package com.afurtak.ultimatetictactoe.game

import android.content.Context
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import com.afurtak.ultimatetictactoe.R

class GameBoard : GridLayout {

    val depth: Int
    val it: Int
    var children: Array<GameBoard>? = null
    var fields: Array<TicTacToeField>? = null
    var parent: GameBoard = this
    var coordinates: Array<Int> = arrayOf()
    var state = BoardState.Empty
    var gameManager: GameManager? = null
    get() {
        return if (isRoot())
            field
        else
            getRoot().gameManager
    }

    private constructor(context: Context, depth: Int, parent: GameBoard, coordinates: Array<Int>, it: Int) : super(context) {
        this.coordinates = coordinates
        this.it = it
        this.depth = depth
        this.parent = parent
        createBoard()
    }

    constructor(context: Context) : this(context, 0, GameManager())

    constructor(context: Context, depth: Int, gameManager: GameManager) : super(context) {
        parent = this
        it = 0
        this.depth = depth
        this.gameManager = gameManager
        createBoard()
    }

    init {
        rowCount = 3
        columnCount = 3
        setBackgroundResource(R.drawable.plane)
    }

    private fun createBoard() {
        if (depth == 0) {
            fields = Array(9) { index: Int ->
                val button = TicTacToeField(context, this, coordinates.copyAndAppend(arrayOf(index)), index).apply {
                    addGridLayoutUndefinedSpecLayoutParams()
                    activate()
                    setOnClickListener {
                        makeMove(index)
                    }
                }
                addView(button)
                button
            }
        } else {
            children = Array(9, { index: Int ->
                val child = GameBoard(context, depth - 1, this, coordinates.copyAndAppend(arrayOf(index)), index)
                child.addGridLayoutUndefinedSpecLayoutParams()
                this@GameBoard.addView(child)
                child
            })
        }
    }

    fun TicTacToeField.makeMove(index: Int) {
        this.setAsClicked(gameManager!!.currentPlayer)
        val t = updateBoardRecursive(index)
        val v = getViewByCoordinates(t)
        gameManager!!.changePlayer()
        if (v is GameBoard) {
            getRoot().deactivate()
            if (v.state != BoardState.Empty)
                v.parent.activate()
            else
                v.activate()
        }
        gameManager!!.addToHistory(this.coordinates)
    }

    /**
     * Activate recursively all children.
     */
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

    /**
     * Deactivate recursively all children.
     */
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
     * and if any of them does, change clickedState of board.
     *
     * @return true if clickedState of the board was not changed and false otherwise
     */
    fun updateState(): Boolean {
        if (state != BoardState.Empty)
            return false

        val fieldsValues = if (depth == 0) {
            Array(9) {
                when (fields!![it].clickedState) {
                    BoardState.Cross -> 1
                    BoardState.Circle -> 7
                    else -> 0
                }
            }
        }
        else {
            Array(9) {
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
                setAsWon(state)
                return true
            }
            else if (v == 21) {
                state = BoardState.Circle
                setAsWon(state)
                return true
            }
        }
        return false
    }

    /**
     * Marks all children-buttons as won
     */
    private fun setAsWon(state: BoardState) {
        if (depth == 0) {
            for (field in fields!!)
                field.setAsWon(state)
        }
        else
            for (child in children!!)
                child.setAsWon(state)
    }

    /**
     * Updates board recursive up to the root.
     * If clickedState changes in root, will call endOfTheGame method which should finish game.
     *
     * @return coordinates of the deepest board where clickedState is empty or is not root
     */
    fun updateBoardRecursive(from: Int): Array<Int> {
        if (isRoot()) {
            if (updateState())
                endOfTheGame()
        }
        else if (updateState()) {
            if (!parent.isRoot())
                return parent.updateBoardRecursive(it)
            else if (parent.updateState()) {
                endOfTheGame()
            }
        }

        if (!isRoot())
            return coordinates.copy().apply {
                this[lastIndex] = from
            }
        else
            return arrayOf()
    }

    /**
     * @return root of the GameBoard instance.
     */
    fun getRoot(): GameBoard {
        return if (parent.depth == this.depth)
            this
        else
            parent.getRoot()
    }

    /**
     * @return true if the instance of GameBoard on which was
     *         called the method isn't child board of any GameBoard.
     */
    fun isRoot(): Boolean {
        return parent.depth == this.depth
    }

    /**
     * Recursive method that returns GameBoard or button
     * from GameBoard having given coordinates.
     *
     * @return View class instance which should be casted
     *         to GameBoard, or TicTacToeField.
     */
    fun getViewByCoordinates(coordinates: Array<Int>, currentCoordinate: Int = 0): View {
        if (currentCoordinate == 0 && !isRoot())
            return getRoot().getViewByCoordinates(coordinates)

        if (currentCoordinate >= coordinates.size)
            return this

        return if (depth == 0)
            fields!![coordinates[currentCoordinate]]
        else {
            if (currentCoordinate == coordinates.size - 1)
                children!![coordinates.last()]
            else
                children!![coordinates[currentCoordinate]]
                        .getViewByCoordinates(coordinates, currentCoordinate + 1)
        }
    }

    fun endOfTheGame() {
        Toast.makeText(context, "END OF THE GAME", Toast.LENGTH_LONG).show()
    }



    fun undo() {
        if (gameManager!!.hasHistory()) {
            val lastMove = getLastMove()
            var lastField = getViewByCoordinates(lastMove) as TicTacToeField
            lastField.setAsNotClicked()

            var board = lastField.parent
            while (!board.isRoot() && board.parent.state != BoardState.Empty)
                board = board.parent

            board.undoBoardRecursive()

            gameManager!!.changePlayer()
            gameManager!!.undo()

            if (gameManager!!.hasHistory()) {
                var coordinates = gameManager!!.getLastCoordinates()
                lastField = getViewByCoordinates(coordinates) as TicTacToeField
                coordinates = lastField.parent.updateBoardRecursive(coordinates.last())
                val v = getViewByCoordinates(coordinates)
                if (v is GameBoard) {
                    getRoot().deactivate()
                    if (v.state != BoardState.Empty)
                        v.parent.activate()
                    else
                        v.activate()
                }
            }
            else {
                getRoot().activate()
            }
        }

    }

    fun undoBoardRecursive() {
        state = BoardState.Empty
        if (depth == 0)
            for (field in fields!!)
                field.undoSetAsWon()
        else
            for (child in children!!)
                child.undoBoardRecursive()
    }

    fun getLastMove() : Array<Int> {
        return gameManager!!.getLastCoordinates()
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