package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityAboutBinding
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityWinnerBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAboutBinding
    val TAG = "AboutActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAbout.text = "Nyahallo place about here"

        binding.btnBack.setOnClickListener {
            finish()
        }

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