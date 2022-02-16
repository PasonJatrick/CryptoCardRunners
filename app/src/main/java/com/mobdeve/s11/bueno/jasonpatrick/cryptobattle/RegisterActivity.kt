package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityRegisterBinding
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference
    lateinit var sharedPrefUtility: ShareprefUtility
    val TAG = "RegisterActivity"
    private val emailPattern =  Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isValidString(str: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAbout.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            database = FirebaseDatabase.getInstance("https://cryptobattle-bfcb0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
            database.child(username).get().addOnSuccessListener {
                var duplicate = false
                if(it.exists()) {
                    Toast.makeText(this, "Username is already taken", Toast.LENGTH_SHORT).show()
                } else {
                    val User = User(email,username,password)

                    if (isValidString(email.trim { it <= ' '}) == true &&
                        username.trim { it <= ' '}.length > 0 &&
                        password.trim{ it <= ' '}.length > 7) {

                        database.child(username).setValue(User).addOnSuccessListener {
                            binding.etEmail.text.clear()
                            binding.etUsername.text.clear()
                            binding.etPassword.text.clear()
                            Toast.makeText(applicationContext, "Successfully registered", Toast.LENGTH_SHORT).show()
                            finish()
                        }.addOnFailureListener{
                            Toast.makeText(applicationContext, "Registration Failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        var error = ""
                        if (isValidString(email.trim { it <= ' '}) == false)
                            error += "Invalid email format\n"
                        if (username.trim { it <= ' '}.length <= 0)
                            error += "Username must be greater than 1 character\n"
                        if (password.trim{ it <= ' '}.length <= 7)
                            error += "Password must be greater than 7 characters\n"

                        binding.tvPrompt.text = error

                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Database connection failed", Toast.LENGTH_SHORT).show()
            }
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