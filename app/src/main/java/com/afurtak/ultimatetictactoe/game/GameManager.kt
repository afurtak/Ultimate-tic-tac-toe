package com.afurtak.ultimatetictactoe.game

class GameManager(var currentPlayer: BoardState) {

    constructor() : this(BoardState.Cross)

    fun changePlayer() {
        currentPlayer = if (currentPlayer == BoardState.Cross)
            BoardState.Circle
        else
            BoardState.Cross
    }
}