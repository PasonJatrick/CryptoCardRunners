package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityViewPackBinding
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityWinnerBinding

class ViewPackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewPackBinding
    lateinit var sharedPrefUtility: ShareprefUtility
    val TAG = "ViewPackActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrefs()

        binding.btnBack.setOnClickListener {
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
        binding.tvOwner.text = "Owner: " + sharedPrefUtility.getStringPreferences("username")
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