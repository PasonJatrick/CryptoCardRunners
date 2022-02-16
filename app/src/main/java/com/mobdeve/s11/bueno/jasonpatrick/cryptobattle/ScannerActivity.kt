package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle


import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityScannerBinding
import kotlinx.android.synthetic.main.activity_scanner.*
import java.util.regex.Pattern


private const val CAMERA_REQUEST_CODE = 101

class ScannerActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var binding: ActivityScannerBinding
    private lateinit var database: DatabaseReference
    lateinit var sharedPrefUtility: ShareprefUtility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrefs()

        setupPermissions()
        codeScanner()
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, scan_scanner)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = true

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    binding.tvCard.text = it.text
                    var text = tv_card.text.toString()
                    if (text.contains("CRYPTO BATTLE NFT") == true) {
                        var str = text.drop(18)
                        val delim =" "
                        var arr = Pattern.compile(delim).split(str)
                        var i = 0
                        var g = 0
                        var cardname = ""
                        var R = 0
                        var G = 0
                        var B = 0
                        var username = sharedPrefUtility.getStringPreferences("username").toString()
                        var Card = Card(cardname,R,G,B, username)
                        var path = "Decks"

                        binding.btnRedeem.isClickable = true
                        binding.btnRedeem.setBackgroundColor(Color.parseColor("Blue"))
                        binding.btnRedeem.setOnClickListener {

                            arr.forEach {
                                if (i % 5 == 0 && i == 0) {
                                    cardname = it.toString()
                                    g++
                                } else if (i % 5 == 0) {
                                    cardname = it.drop(1)
                                    g++
                                } else if ((i+1) % 5 == 0) {
                                    R = it.toInt()
                                    g++
                                } else if ((i+2) % 5 == 0) {
                                    G = it.toInt()
                                    g++
                                } else if ((i+3) % 5 ==0) {
                                    B = it.toInt()
                                    g++
                                }

                                if (g == 4) {
                                    Card = Card(cardname,R,G,B,username)
                                    path = username + "/" + cardname + "/"
                                    database = FirebaseDatabase.getInstance("https://cryptobattle-bfcb0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Decks")
                                    database.child(path).setValue(Card).addOnSuccessListener {
                                        Toast.makeText(applicationContext, "Successfully added a card to database", Toast.LENGTH_SHORT).show()
                                    }.addOnFailureListener {
                                        Toast.makeText(applicationContext, "Failed to add a card to database", Toast.LENGTH_SHORT).show()
                                    }
                                    g = 0
                                }
                                i++
                            }
                            finish()
                        }
                    } else {
                        binding.btnRedeem.isClickable = false
                        binding.btnRedeem.setBackgroundColor(Color.parseColor("Grey"))
                    }
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }

        scan_scanner.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    fun initPrefs(){
        sharedPrefUtility = ShareprefUtility(this)
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onStop() {
        codeScanner.releaseResources()
        super.onStop()
    }

    override fun onDestroy() {
        codeScanner.releaseResources()
        super.onDestroy()
    }

    private fun setupPermissions(){
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Grant permissions to the camera to use the QR Scanner", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}