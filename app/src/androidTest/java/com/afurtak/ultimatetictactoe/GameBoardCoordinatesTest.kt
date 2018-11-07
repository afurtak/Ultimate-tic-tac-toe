package com.afurtak.ultimatetictactoe

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.afurtak.ultimatetictactoe.gameboard.GameBoard

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class GameBoardCoordinatesTest {
    @Test
    fun test() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val gameBoard = GameBoard(appContext, 1)

        val coordinates = arrayOf(
                arrayOf(),
                arrayOf(0),
                arrayOf(1),
                arrayOf(2),
                arrayOf(3),
                arrayOf(4),
                arrayOf(5),
                arrayOf(6),
                arrayOf(7),
                arrayOf(8),
                arrayOf(0, 0),
                arrayOf(0, 1),
                arrayOf(0, 2),
                arrayOf(0, 3),
                arrayOf(0, 4),
                arrayOf(0, 5),
                arrayOf(0, 6),
                arrayOf(0, 7),
                arrayOf(0, 8),
                arrayOf(1, 0),
                arrayOf(1, 1),
                arrayOf(1, 2),
                arrayOf(1, 3),
                arrayOf(1, 4),
                arrayOf(1, 5),
                arrayOf(1, 6),
                arrayOf(1, 7),
                arrayOf(1, 8),
                arrayOf(2, 0),
                arrayOf(2, 1),
                arrayOf(2, 2),
                arrayOf(2, 3),
                arrayOf(2, 4),
                arrayOf(2, 5),
                arrayOf(2, 6),
                arrayOf(2, 7),
                arrayOf(2, 8),
                arrayOf(3, 0),
                arrayOf(3, 1),
                arrayOf(3, 2),
                arrayOf(3, 3),
                arrayOf(3, 4),
                arrayOf(3, 5),
                arrayOf(3, 6),
                arrayOf(3, 7),
                arrayOf(3, 8),
                arrayOf(4, 0),
                arrayOf(4, 1),
                arrayOf(4, 2),
                arrayOf(4, 3),
                arrayOf(4, 4),
                arrayOf(4, 5),
                arrayOf(4, 6),
                arrayOf(4, 7),
                arrayOf(4, 8),
                arrayOf(5, 0),
                arrayOf(5, 1),
                arrayOf(5, 2),
                arrayOf(5, 3),
                arrayOf(5, 4),
                arrayOf(5, 5),
                arrayOf(5, 6),
                arrayOf(5, 7),
                arrayOf(5, 8),
                arrayOf(6, 0),
                arrayOf(6, 1),
                arrayOf(6, 2),
                arrayOf(6, 3),
                arrayOf(6, 4),
                arrayOf(6, 5),
                arrayOf(6, 6),
                arrayOf(6, 7),
                arrayOf(6, 8),
                arrayOf(7, 0),
                arrayOf(7, 1),
                arrayOf(7, 2),
                arrayOf(7, 3),
                arrayOf(7, 4),
                arrayOf(7, 5),
                arrayOf(7, 6),
                arrayOf(7, 7),
                arrayOf(7, 8),
                arrayOf(8, 0),
                arrayOf(8, 1),
                arrayOf(8, 2),
                arrayOf(8, 3),
                arrayOf(8, 4),
                arrayOf(8, 5),
                arrayOf(8, 6),
                arrayOf(8, 7),
                arrayOf(8, 8)
        )



        val gameBoardCoordinates = arrayListOf<Array<Int>>(arrayOf())
        coordinates.print("coordinates")

        gameBoard.getCoordinates(gameBoardCoordinates)
        gameBoardCoordinates.toTypedArray().print("gameBoardCoordinates")
        assertEquals(coordinates.size, gameBoardCoordinates.size)
        for (i in 0 until coordinates.size) {
            assertEquals(coordinates[i].size, gameBoardCoordinates[i].size)
            for (j in 0 until coordinates[i].size) {
                assertEquals(coordinates[i][j], gameBoardCoordinates[i][j]);
            }
        }
    }
}

fun GameBoard.getCoordinates(result: ArrayList<Array<Int>> = ArrayList()) {
    if (depth == 0) {
        for (t in fields!!) {
            result.add(t.coordinates)
        }
    }
    else {
        for (t in children!!)
            result.add(t.coordinates)

        for (t in children!!)
            t.getCoordinates(result)
    }
}

fun Array<Array<Int>>.print(prefix: String) {
    for (array in this) {
        println("$prefix: ${array.joinToString(", ")}")
    }
}

fun pow(a: Int, b: Int) : Int {
    if (b == 0)
        return 1
    return a * pow(a, b - 1)
}