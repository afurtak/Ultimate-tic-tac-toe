package com.afurtak.ultimatetictactoe.game

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afurtak.ultimatetictactoe.R
import kotlinx.android.synthetic.main.game_fragment.view.*

class GameFragment : Fragment() {

    companion object {
        const val DEPTH_KEY = "depth_game_fragment_key_bundle"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val depth = when {
            arguments != null -> if (arguments!!.containsKey(DEPTH_KEY))
                arguments!!.getInt(DEPTH_KEY)
            else
                0
            else -> 0
        }
        val fragmentView = inflater.inflate(R.layout.game_fragment, container, false)

        val gameManager = GameManager {
            Toast.makeText(context, "Changing player", Toast.LENGTH_LONG).show()
        }
        val gameBoard = GameBoard(fragmentView.context, depth, gameManager)
        gameBoard.layoutParams = ViewGroup.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        fragmentView.gameBoardLayout.addView(gameBoard)
        fragmentView.undoButton.setOnClickListener {
            gameBoard.undo()
        }

        return fragmentView
    }
}

