package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityPickStatsBinding

class PickStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPickStatsBinding
    lateinit var sharedPrefUtility: ShareprefUtility
    val TAG = "PickStatsActivity"
    var pick1 = 0
    var pick2 = 0
    var stat = 0
    var action = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrefs()

        var HP = 5
        var Player = sharedPrefUtility.getStringPreferences("Player")
        if (Player == "Player 1")
            HP = sharedPrefUtility.getIntPreferences("HP1")
        else
            HP = sharedPrefUtility.getIntPreferences("HP2")

        binding.tvPrompt.text = "$HP HP Left. Pick Your Card"

        binding.btnPickR.setOnClickListener {
            pick1 = 1
            binding.btnPickR.setTextColor(Color.parseColor("red"))
            binding.btnPickG.setTextColor(Color.parseColor("white"))
            binding.btnPickB.setTextColor(Color.parseColor("white"))
            binding.tvSelectstat.setText("Stat you picked: R")

            stat = sharedPrefUtility.getIntPreferences("R")
        }
        binding.btnPickG.setOnClickListener {
            pick1 = 1
            binding.btnPickR.setTextColor(Color.parseColor("white"))
            binding.btnPickG.setTextColor(Color.parseColor("green"))
            binding.btnPickB.setTextColor(Color.parseColor("white"))
            binding.tvSelectstat.setText("Stat you picked: G")

            stat = sharedPrefUtility.getIntPreferences("G")
        }
        binding.btnPickB.setOnClickListener {
            pick1 = 1
            binding.btnPickR.setTextColor(Color.parseColor("white"))
            binding.btnPickG.setTextColor(Color.parseColor("white"))
            binding.btnPickB.setTextColor(Color.parseColor("aqua"))
            binding.tvSelectstat.setText("Stat you picked: B")

            stat = sharedPrefUtility.getIntPreferences("B")
        }

        binding.btnPickup.setOnClickListener {
            pick2 = 1
            binding.btnPickup.setTextColor(Color.parseColor("yellow"))
            binding.btnPickdown.setTextColor(Color.parseColor("white"))
            binding.tvSelectmod.setText("Mod You Picked: Up")

            sharedPrefUtility.saveStringPreferences("modifier", "Up")
        }
        binding.btnPickdown.setOnClickListener {
            pick2 = 1
            binding.btnPickup.setTextColor(Color.parseColor("white"))
            binding.btnPickdown.setTextColor(Color.parseColor("yellow"))
            binding.tvSelectmod.setText("Mod You Picked: Down")

            sharedPrefUtility.saveStringPreferences("modifier", "Down")
        }

        binding.btnConfirm.setOnClickListener {
            if ((pick1 == 0 || pick2 == 0) && action == "Attacking") {
                Toast.makeText(this, "Choose a stat and a modifier", Toast.LENGTH_SHORT).show()
            } else if (pick1 == 0 && action =="Defending") {
                Toast.makeText(this, "Choose a stat", Toast.LENGTH_SHORT).show()
            } else {
                var player = sharedPrefUtility.getStringPreferences("player")
                var action = sharedPrefUtility.getStringPreferences("action")
                var round = sharedPrefUtility.getIntPreferences("round")
                round++

                if (player == "Player 2") {
                    sharedPrefUtility.saveIntPreferences("statP2", stat)
                    sharedPrefUtility.saveStringPreferences("player", "Player 1")
                    sharedPrefUtility.saveIntPreferences("round",round)
                } else {
                    sharedPrefUtility.saveIntPreferences("statP1", stat)
                    sharedPrefUtility.saveStringPreferences("player", "Player 2")
                    sharedPrefUtility.saveIntPreferences("round",round)
                }

                if (action == "Attacking")
                    sharedPrefUtility.saveStringPreferences("action", "Defending")
                else
                    sharedPrefUtility.saveStringPreferences("action", "Attacking")

                if (round % 2 == 1){
                    if (action == "Attacking")
                        sharedPrefUtility.saveStringPreferences("action", "Attacking")
                    else
                        sharedPrefUtility.saveStringPreferences("action", "Defending")
                }
                finish()
            }
        }
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

        var side = sharedPrefUtility.getStringPreferences("side")
        var card = sharedPrefUtility.getStringPreferences("cardname")
        var owner = sharedPrefUtility.getStringPreferences("owner")

        if (side == "left") {
            binding.ivCard.setImageResource(R.drawable.doodle_angel)
        } else {
            binding.ivCard.setImageResource(R.drawable.doodle_devil)
        }

        binding.tvCardname.setText("" + card + "\n" + "Owner: " + owner)
        binding.tvCardR.setText("R: " + sharedPrefUtility.getIntPreferences("R").toString())
        binding.tvCardG.setText("G: " + sharedPrefUtility.getIntPreferences("G").toString())
        binding.tvCardB.setText("B: " + sharedPrefUtility.getIntPreferences("B").toString())

        action = sharedPrefUtility.getStringPreferences("action").toString()
        if (action == "Defending") {
            binding.btnPickup.isClickable = false
            binding.btnPickdown.isClickable = false
            binding.btnPickup.setBackgroundColor(Color.parseColor("Grey"))
            binding.btnPickdown.setBackgroundColor(Color.parseColor("Grey"))
        } else {
            binding.btnPickup.isClickable = true
            binding.btnPickdown.isClickable = true
            binding.btnPickup.setBackgroundColor(Color.parseColor("blue"))
            binding.btnPickdown.setBackgroundColor(Color.parseColor("blue"))
        }

    }

    override fun onPause(){
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}