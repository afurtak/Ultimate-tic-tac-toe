package com.afurtak.ultimatetictactoe.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.afurtak.ultimatetictactoe.R
import com.afurtak.ultimatetictactoe.game.GameFragment

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        val depth = intent.getIntExtra(MenuActivity.DEPTH_OF_GAME_KEY, 1)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val fragment = GameFragment()
        val arguments = Bundle()
        arguments.putInt(GameFragment.DEPTH_KEY, depth)
        fragment.arguments = arguments

        transaction.add(R.id.gameActivityGameLayout, fragment)

        transaction.commit()

    }
}
