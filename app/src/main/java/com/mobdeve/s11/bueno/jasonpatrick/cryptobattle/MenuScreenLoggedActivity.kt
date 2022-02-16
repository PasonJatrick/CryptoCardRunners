package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityMenuScreenLoggedBinding

class MenuScreenLoggedActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMenuScreenLoggedBinding
    lateinit var sharedPrefUtility: ShareprefUtility
    val TAG ="MenuScreenLogged"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuScreenLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrefs()

        binding.btnPlay.setOnClickListener {
            var gotoGetReadyActivity = Intent(applicationContext, GetReadyActivity::class.java)
            startActivity(gotoGetReadyActivity)
        }

        binding.btnProfile.setOnClickListener {
            var gotoProfileActivity = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(gotoProfileActivity)
        }

        binding.btnRedeem.setOnClickListener {
            var gotoScannerActivity = Intent(applicationContext, ScannerActivity::class.java)
            startActivity(gotoScannerActivity)
        }

        binding.btnView.setOnClickListener {
            var gotoViewPackActivity = Intent(applicationContext, ViewPackActivity::class.java)
            startActivity(gotoViewPackActivity)
        }

        binding.btnTrade.setOnClickListener {
            var gotoScannerActivity = Intent(applicationContext, ScannerActivity::class.java)
            startActivity(gotoScannerActivity)
        }

        binding.btnAbout.setOnClickListener {
            var gotoAboutActivity = Intent(applicationContext, AboutActivity::class.java)
            startActivity(gotoAboutActivity)
        }

        binding.btnLogout.setOnClickListener {
            var gotoMenuScreenNoLogActivity = Intent(applicationContext, MenuScreenNoLogActivity::class.java)
            startActivity(gotoMenuScreenNoLogActivity)
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
        binding.tvUser.text = "Welcome, " + sharedPrefUtility.getStringPreferences("username")
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