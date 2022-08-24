package com.example.lifecyleeventsanduistate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifecyleeventsanduistate.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var coinMain: Int = 0
    private val Coin by lazy { binding.etxtNumber}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bttnStart.setOnClickListener{
            if(Coin.text.toString().isNotEmpty()){

                coinMain = Integer.parseInt(Coin.text.toString())
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra(COIN_MAIN, coinMain)
                startActivityForResult(intent, REQUEST_CODE)
            }
            else{
                Snackbar.make(it, "Please enter Something!", Snackbar.LENGTH_LONG).show()
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            val returnValue = data?.getIntExtra(RETURN_KEY, 0)
            Snackbar.make(findViewById(android.R.id.content), "You have a total of: $returnValue coins" , Snackbar.LENGTH_LONG).show()
            coinMain = returnValue!!
            Coin.setText(coinMain.toString())
        }
    }
}