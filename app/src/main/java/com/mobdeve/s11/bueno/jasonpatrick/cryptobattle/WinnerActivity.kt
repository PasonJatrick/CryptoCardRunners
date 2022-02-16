package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityPickStatsBinding
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityWinnerBinding

class WinnerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWinnerBinding
    lateinit var sharedPrefUtility: ShareprefUtility
    val TAG = "WinnerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrefs()

        var winner = sharedPrefUtility.getStringPreferences("Winner")
        binding.tvPlayer.text = "$winner has won the game"

        binding.Winner.setOnClickListener {
            finish()
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