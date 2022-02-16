package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityGetReadyBinding

class GetReadyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetReadyBinding
    private lateinit var database: DatabaseReference
    lateinit var sharedPrefUtility: ShareprefUtility

    val TAG = "AboutActivity"
    var player = "Player 1"
    var action = "Attacking"
    var round = 1
    var HP1 = 10
    var HP2 = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetReadyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrefs()

        binding.tvPlayer.text = "Your Turn " + player + "\n\nYou are " + action + "\n\nRound " + round

        binding.GetReady.setOnClickListener {
            var gotoChooseCardActivity = Intent(applicationContext, ChooseCardActivity::class.java)
            startActivity(gotoChooseCardActivity)
        }

        sharedPrefUtility.saveIntPreferences("HP1", HP1)
        sharedPrefUtility.saveIntPreferences("HP2", HP2)

    }

    fun initPrefs(){
        sharedPrefUtility = ShareprefUtility(this)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")

        round = sharedPrefUtility.getIntPreferences("round")
        if (round > 1) {
            player = sharedPrefUtility.getStringPreferences("player").toString()
            action = sharedPrefUtility.getStringPreferences("action").toString()
        }
            binding.tvPlayer.text = "Your Turn " + player + "\n\nYou are " + action + "\n\nRound " + (round+1)/2

        if ( ((round+1)%2 == 0) && round !=1 ) {
            var statP1 = sharedPrefUtility.getIntPreferences("statP1")
            var statP2 = sharedPrefUtility.getIntPreferences("statP2")
            var modifier = sharedPrefUtility.getStringPreferences("modifier").toString()
            var HP1 = sharedPrefUtility.getIntPreferences("HP1")
            var HP2 = sharedPrefUtility.getIntPreferences("HP2")
            var winner = "none"

            if (modifier == "Up") {
                if (statP1 > statP2) {
                    winner = "Player 1 wins the round"
                    HP2 --
                    sharedPrefUtility.saveIntPreferences("HP2", HP2)
                } else {
                    winner = "Player 2 wins the round"
                    HP1 --
                    sharedPrefUtility.saveIntPreferences("HP2", HP1)
                }
            } else {
                if (statP1 < statP2) {
                    winner = "Player 1 wins the round"
                    HP2 --
                    sharedPrefUtility.saveIntPreferences("HP2", HP2)
                }  else {
                    winner = "Player 2 wins the round"
                    HP1 --
                    sharedPrefUtility.saveIntPreferences("HP1", HP1)
                }
            }

            if (HP1 == 0 || HP2 == 0) {
                if (HP1 == 0)
                    sharedPrefUtility.saveStringPreferences("Winner", "Player 2")
                else
                    sharedPrefUtility.saveStringPreferences("Winner", "Player 1")
                var gotoWinnerActivity = Intent(applicationContext, WinnerActivity::class.java)
                startActivity(gotoWinnerActivity)
                finish()
            }




            Toast.makeText(this, "statP1: " + statP1 + "\nstatP2: " + statP2 + "\nModifier: " + modifier,Toast.LENGTH_SHORT).show()
            binding.tvPlayer.text = winner + "\n\nYour Turn " + player + "\n\nYou are " + action + "\n\nRound " + (round+1)/2
        }
    }

    override fun onPause(){
        super.onPause()
        Log.i(TAG, "onPause")
        sharedPrefUtility.saveStringPreferences("player", player)
        sharedPrefUtility.saveStringPreferences("action", action)
        sharedPrefUtility.saveIntPreferences("round", round)
        }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
        Toast.makeText(this, "Game Closed", Toast.LENGTH_SHORT).show()
        sharedPrefUtility.removeAllPreferences()
    }

}