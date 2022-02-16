package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityMenuScreenNoLogBinding

class MenuScreenNoLogActivity : AppCompatActivity() {

    var binding:ActivityMenuScreenNoLogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuScreenNoLogBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        binding!!.btnLogin.setOnClickListener {
            val gotoLoginActivity = Intent(applicationContext, LoginActivity::class.java)
            startActivity(gotoLoginActivity)
        }

        binding!!.btnRegister.setOnClickListener {
            val gotoRegisterActivity = Intent (applicationContext, RegisterActivity::class.java)
            startActivity(gotoRegisterActivity)
        }

        binding!!.btnAbout.setOnClickListener {
            val gotoAboutActivity = Intent(applicationContext, AboutActivity::class.java)
            startActivity(gotoAboutActivity)
        }
    }
}