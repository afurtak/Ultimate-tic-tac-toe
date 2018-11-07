package com.afurtak.ultimatetictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val fragment = GameFragment()
        val arguments = Bundle()
        arguments.putInt(GameFragment.DEPTH_KEY, 1)
        fragment.arguments = arguments

        transaction.add(R.id.gameActivityGameLayout, fragment)

        transaction.commit()

    }
}
