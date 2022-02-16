package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityLoginBinding
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityWinnerBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference
    lateinit var sharedPrefUtility: ShareprefUtility
    val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrefs()

        binding.btnAbout.setOnClickListener {
            finish()
        }

        binding.btnLogin.setOnClickListener {
            var username = binding.etUsername.text.toString()
            var password = binding.etPassword.text.toString()

            database = FirebaseDatabase.getInstance("https://cryptobattle-bfcb0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
            database.child(username).get().addOnSuccessListener {
                if (it.exists()) {
                    val user = it.child("username").value
                    val pass = it.child("password").value

                    if (password == pass && username == user) {
                        var gotoMenuScreenLoggedActivity = Intent(applicationContext, MenuScreenLoggedActivity::class.java)
                        startActivity(gotoMenuScreenLoggedActivity)
                        sharedPrefUtility.saveStringPreferences("username", user)
                        finish()
                    } else {
                        binding.tvPrompt.text = "Incorrect username/password"
                    }

                } else {
                    binding.tvPrompt.text = "Incorrect username/password"
                }

            }.addOnFailureListener{
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
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