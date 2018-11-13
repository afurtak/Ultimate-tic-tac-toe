package com.afurtak.ultimatetictactoe.game

class GameManager(var currentPlayer: BoardState, val callAfterChangePlayer: () -> Unit) {

    constructor() : this(BoardState.Cross, {})

    constructor(lambda: () -> Unit) : this(BoardState.Cross, lambda)


    fun changePlayer() {
        callAfterChangePlayer()
        currentPlayer = if (currentPlayer == BoardState.Cross)
            BoardState.Circle
        else
            BoardState.Cross
    }
}