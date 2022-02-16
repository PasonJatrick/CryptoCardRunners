package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var handler:Handler

    var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        val logoUri = Uri.parse("android.resource://" + packageName + "/"+ R.raw.crypto_card_runners_logo_breathe)
        val doodleUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.doodles_angel_square)
        val afUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.af_angel_square)

        binding!!.vvLogo.setVideoURI(logoUri)
        binding!!.vvLogo.start()
        binding!!.vvDoodle.setVideoURI(doodleUri)
        binding!!.vvDoodle.start()
        binding!!.vvAf.setVideoURI(afUri)
        binding!!.vvAf.start()

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            var gotoMenuScreenNoLogActivity = Intent(applicationContext, MenuScreenNoLogActivity::class.java)
            startActivity(gotoMenuScreenNoLogActivity)
            finish()
        }, 5000)    //delaying 5 seconds to open menu screen
    }
}