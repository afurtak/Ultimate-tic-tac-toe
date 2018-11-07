package com.afurtak.ultimatetictactoe

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afurtak.ultimatetictactoe.gameboard.GameBoard
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

        val gameBoard = GameBoard(fragmentView.context, depth)
        gameBoard.layoutParams = ViewGroup.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        fragmentView.gameFragmentLayout.addView(gameBoard)

        return fragmentView
    }
}

