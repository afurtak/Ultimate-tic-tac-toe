package com.afurtak.ultimatetictactoe.game

import java.util.*
import kotlin.coroutines.experimental.coroutineContext

class GameManager(var currentPlayer: BoardState, val callAfterChangePlayer: () -> Unit) {

    constructor() : this(BoardState.Cross, {})

    constructor(lambda: () -> Unit) : this(BoardState.Cross, lambda)

    val history = Stack<Array<Int>>()

    fun changePlayer() {
        callAfterChangePlayer()
        currentPlayer = if (currentPlayer == BoardState.Cross)
            BoardState.Circle
        else
            BoardState.Cross
    }

    fun hasHistory() = !history.empty()

    fun undo() {
        history.pop()
    }

    fun getLastCoordinates(): Array<Int> {
        return history.peek().clone()
    }

    fun addToHistory(coordinates: Array<Int>) {
        history.add(coordinates.clone())
    }
}