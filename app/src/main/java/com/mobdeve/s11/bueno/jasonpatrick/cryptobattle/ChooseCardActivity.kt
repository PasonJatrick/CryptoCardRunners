package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mobdeve.s11.bueno.jasonpatrick.cryptobattle.databinding.ActivityChooseCardBinding
import java.io.File
import java.util.*
import java.util.regex.Pattern

class ChooseCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseCardBinding
    private lateinit var database: DatabaseReference
    private lateinit var storageReference: StorageReference
    lateinit var sharedPrefUtility: ShareprefUtility
    var deck1 = mutableListOf<Card>()
    val TAG = "AboutActivity"
    var right = 0
    var left = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrefs()
        var username = sharedPrefUtility.getStringPreferences("username").toString()

        database = FirebaseDatabase.getInstance("https://cryptobattle-bfcb0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Decks")

        database.child(username).addValueEventListener (object: ValueEventListener {
            override fun onDataChange (dataSnapshot: DataSnapshot) {
                var i = 0
                val cards: Iterator<DataSnapshot> = dataSnapshot.children.iterator()
                while (cards.hasNext()) {
                    var card: DataSnapshot = cards.next()
                    var owner = card.child("owner").value
                    var cardname = card.child("cardname").value
                    var R = card.child("r").value
                    var G = card.child("g").value
                    var B = card.child("b").value
                    val delim = " "
                    var str = "$cardname $R $G $B $owner"
                    var arr = Pattern.compile(delim).split(str)
                    deck1.add(i, Card(arr[0],arr[1].toInt(),arr[2].toInt(),arr[3].toInt(),arr[4]) )
                    i++
                }
                var rightrand = (0 until 9).random()
                var leftrand = (0 until 9).random()
                while (rightrand == leftrand)
                    leftrand = (0 until 9).random()

                binding.tvRightowner.text = "owner: " + deck1[rightrand].owner
                binding.tvRightname.text = "name: " + deck1[rightrand].cardname
                binding.tvRightR.text = "R: " + deck1[rightrand].R
                binding.tvRightG.text = "G: " + deck1[rightrand].G
                binding.tvRightB.text = "B: " + deck1[rightrand].B
                binding.tvLeftowner.text = "owner: " + deck1[leftrand].owner
                binding.tvLeftname.text = "name: " + deck1[leftrand].cardname
                binding.tvLeftR.text = "R: " + deck1[leftrand].R
                binding.tvLeftG.text = "G: " + deck1[leftrand].G
                binding.tvLeftB.text = "B: " + deck1[leftrand].B

                var rightcard =  deck1[rightrand].cardname + ".png"
                var leftcard =  deck1[leftrand].cardname + ".png"
                Toast.makeText(applicationContext, "$rightcard", Toast.LENGTH_SHORT).show()
                storageReference = FirebaseStorage.getInstance("gs://cryptobattle-bfcb0.appspot.com").reference.child(rightcard)
                var localFile = File.createTempFile("tempImage", "png" )
                storageReference.getFile(localFile).addOnSuccessListener {

                    var bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    binding.ivRight.setImageBitmap(bitmap)
                }.addOnFailureListener{
                    Toast.makeText(applicationContext, "$leftcard $rightcard", Toast.LENGTH_SHORT).show()
                }

                storageReference = FirebaseStorage.getInstance("gs://cryptobattle-bfcb0.appspot.com").reference.child(leftcard)
                var localFile2 = File.createTempFile("tempImage2", "png" )
                storageReference.getFile(localFile2).addOnSuccessListener {

                    var bitmap2 = BitmapFactory.decodeFile(localFile2.absolutePath)
                    binding.ivLeft.setImageBitmap(bitmap2)
                }.addOnFailureListener{
                    Toast.makeText(applicationContext, "$leftcard $rightcard", Toast.LENGTH_SHORT).show()
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG, "Cancelled loading of database for cards")
            }
        })



        var HP = 5
        var Player = sharedPrefUtility.getStringPreferences("Player")
        if (Player == "Player 1")
            HP = sharedPrefUtility.getIntPreferences("HP1")
        else
            HP = sharedPrefUtility.getIntPreferences("HP2")

        binding.tvPrompt.text = "$HP HP Left. Pick Your Card"

        binding.btnPickleft.setOnClickListener {
            left = 1
            right = 0
            binding.btnPickleft.setTextColor(Color.parseColor("aqua"))
            binding.btnPickright.setTextColor(Color.parseColor("white"))
            binding.tvSelect.setText("You Picked: Left card")
        }
        binding.btnPickright.setOnClickListener {
            right = 1
            left = 0
            binding.btnPickright.setTextColor(Color.parseColor("aqua"))
            binding.btnPickleft.setTextColor(Color.parseColor("white"))
            binding.tvSelect.setText("You Picked: Right card" )
        }

        binding.btnConfirm.setOnClickListener {
            var gotoPickStatsActivity = Intent(applicationContext, PickStatsActivity::class.java)
            if (right == 1 || left == 1) {
                if (right == 1) {
                    sharedPrefUtility.saveStringPreferences("side", "right")
                    sharedPrefUtility.saveStringPreferences("cardname", binding.tvRightname.text.toString())
                    sharedPrefUtility.saveStringPreferences("owner", binding.tvRightowner.text.toString())
                    sharedPrefUtility.saveIntPreferences("R", binding.tvRightR.text.drop(3).toString().toInt())
                    sharedPrefUtility.saveIntPreferences("G", binding.tvRightG.text.drop(3).toString().toInt())
                    sharedPrefUtility.saveIntPreferences("B", binding.tvRightB.text.drop(3).toString().toInt())
                } else {
                    sharedPrefUtility.saveStringPreferences("side", "left")
                    sharedPrefUtility.saveStringPreferences("cardname", binding.tvLeftname.text.toString())
                    sharedPrefUtility.saveStringPreferences("owner", binding.tvRightowner.text.toString())
                    sharedPrefUtility.saveIntPreferences("R", binding.tvLeftR.text.drop(3).toString().toInt())
                    sharedPrefUtility.saveIntPreferences("G", binding.tvLeftG.text.drop(3).toString().toInt())
                    sharedPrefUtility.saveIntPreferences("B", binding.tvLeftB.text.drop(3).toString().toInt())
                }
                startActivity(gotoPickStatsActivity)
                finish()
            } else {
                Toast.makeText(this, "Choose a card", Toast.LENGTH_SHORT).show()
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


