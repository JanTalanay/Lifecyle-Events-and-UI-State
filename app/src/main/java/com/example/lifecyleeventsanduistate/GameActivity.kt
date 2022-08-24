package com.example.lifecyleeventsanduistate

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.example.lifecyleeventsanduistate.databinding.ActivityGameBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var Rimage :IntArray
    private lateinit var messages :String
    private var coins: Int = 10
    private var extra: Int = 0
    ////////////////////////////////////////////////
    private val btnDraw5 by lazy { binding.btndraw5 }
    private val btnDraw10 by lazy { binding.btndraw10 }
    private val txtMessage by lazy { binding.txtmessage}
    private val txtCoins by lazy { binding.txtcoins}
    private val images by lazy {
        listOf(binding.img1, binding.img2, binding.img3)
    }


    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageViews()

        extra = intent.getIntExtra(COIN_MAIN, 0)
        txtCoins.text = "You have $extra Coins"

        messages = savedInstanceState?.getString(MESSAGE) ?: getString(R.string.show_apple)
        Rimage = savedInstanceState?.getIntArray(IMAGES) ?: intArrayOf(R.drawable.empty,R.drawable.empty,R.drawable.empty)
        displayRimages()
        messages()

        btnDraw5.setOnClickListener{
            if(extra > 0) {
                drawSlotMachine()
                draw5()
            }
            else{
                txtCoins.text = "YOU LOSE"
                Snackbar.make(it, "No coins stop betting!", Snackbar.LENGTH_LONG).show()
            }
        }
        btnDraw10.setOnClickListener{
            if(extra > 5) {
                drawSlotMachine()
                draw10()
            }
            else{
                Snackbar.make(it, "No coins stop betting!", Snackbar.LENGTH_LONG).show()
            }
        }
        if(savedInstanceState != null){
            extra = savedInstanceState.getInt("COINS")
            txtCoins.text = "You have $extra Coins"
        }
    }

    override fun finish() {
        val data = Intent()
        data.putExtra(RETURN_KEY, extra)
        setResult(Activity.RESULT_OK, data)
        super.finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.itemShare){
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Luck Apple Slot Machine:" +
                        "\n You Have a total of: $extra coin(s) remaining" +
                        "\n as of ${Calendar.getInstance().time}")
                type = "text/plain"
            }
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray(IMAGES, Rimage)
        outState.putInt("COINS", extra)
        outState.putString(MESSAGE, messages)
        super.onSaveInstanceState(outState)
    }

    private fun draw5(){
        if(appleCount().equals(1)){
            extra += 5
        }
        else if(appleCount().equals(2)){
            extra += 10
        }
        else if(appleCount().equals(3)){
            extra += 15
        }
        else{
            extra -= 5
        }
        txtCoins.text = "You have $extra Coins"
    }

    private fun draw10(){
        if(appleCount().equals(1)){
            extra += 10
        }
        else if(appleCount().equals(2)){
            extra += 20
        }
        else if(appleCount().equals(3)){
            extra += 30
        }
        else{
            extra -= 10
        }
        txtCoins.text = "You have $extra Coins"
    }

    private fun messages(){
        when(appleCount()){
            1 -> messages = "Nice one"
            2 -> messages = "You got two!"
            3 -> messages = "wow you got 3"
            else -> messages = "Give that Apple"
        }
        txtMessage.text = messages
    }

    private fun appleCount():Int{
        var counterApple = 0
        for (i in Rimage){
            if (R.drawable.apple == i){
                counterApple++
            }
        }
        return counterApple
    }

    private fun randomImage():Int{
        val r = Random.nextInt(3)
        return when(r){
            0 -> R.drawable.apple
            1 -> R.drawable.grapes
            2 -> R.drawable.orange
            else -> R.drawable.empty
        }
    }

    private fun setRImage(){
        Rimage = intArrayOf(randomImage(),randomImage(), randomImage())
    }

    private fun drawSlotMachine() {
        setRImage()
        displayRimages()
        messages()
    }

    private fun displayRimages() {
        for (i in 0 until Rimage.size) {
            images[i].setImageResource(Rimage[i])
        }
    }

    private fun imageViews() {
        for(i in images) i.setImageResource(R.drawable.empty)
    }
}
