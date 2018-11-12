package com.afurtak.ultimatetictactoe.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.afurtak.ultimatetictactoe.R

import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.content_menu.*

class MenuActivity : AppCompatActivity() {

    companion object {
        const val DEPTH_OF_GAME_KEY = "DEPTH_OF_GAME_KEY_TO_PUT_EXTRA"
        const val PLAYERS_NUMBER_KEY = "PLAYERS_NUMBER_KEY_TO_PUT_EXTRA"
    }

    var depthOfGame = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        gameDepthInfo.text = depthOfGame.toString()

        increaseGameDepth.setOnClickListener {
            depthOfGame++
            if (depthOfGame > 3)
                depthOfGame = 3

            gameDepthInfo.text = depthOfGame.toString()
        }

        decreaseGameDepth.setOnClickListener {
            depthOfGame--
            if (depthOfGame < 0)
                depthOfGame = 0

            gameDepthInfo.text = depthOfGame.toString()
        }

        startSinglePlayerButton.setOnClickListener {
            startGameActivity(1, depthOfGame)
        }
    }

    fun startGameActivity(playersNumber: Int, depth: Int) {
        val intent = Intent(this, GameActivity::class.java).apply {
            putExtra(PLAYERS_NUMBER_KEY, playersNumber)
            putExtra(DEPTH_OF_GAME_KEY, depth)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
