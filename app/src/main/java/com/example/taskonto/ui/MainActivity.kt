package com.example.taskonto.ui

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.taskonto.R
import com.example.taskonto.data.model.Bookings
import com.example.taskonto.databinding.ActivityMainBinding
import com.example.taskonto.utils.Error
import com.example.taskonto.utils.Success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    var subscriptionMilesLeft : String? = null
    var lastEnergyLevel : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pgMilesLeft.max = 1000
        observe()
        binding.ivRefresh.setOnClickListener {
            binding.tvEnergyLevel.visibility = View.GONE
            mainViewModel.loadEVDataDetails()
        }
    }

    fun observe(){
        mainViewModel.loadEVDataDetails()
        mainViewModel.evDataLivedata.observe(this){
            when(it){
                is Success -> {
                    binding.tvEnergyLevel.visibility = View.VISIBLE
                    it.data.bookingDetails?.let { evData ->
                        subscriptionMilesLeft =  evData[0].subscriptionMilesLeft
                        lastEnergyLevel = evData[0].car?.lastEnergyLevel
                        loadEnergyProgress(lastEnergyLevel)
                        loadMilesProgress(subscriptionMilesLeft)
                    }
                }
                is Error -> {
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT)
                }
                is Exception -> {
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT)
                }
                else -> {
                    Toast.makeText(this,"Error Fetching from Server",Toast.LENGTH_LONG)
                }
            }
        }
    }

     fun loadEnergyProgress(lastEnergyLevel: String?) {
        var lastEnergyLevel : Int? = lastEnergyLevel?.toInt()

       CoroutineScope(Dispatchers.Default).launch {
            if (lastEnergyLevel != null) {
                for (energyStatus in 1..lastEnergyLevel) {
                    try {
                        delay(10)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    withContext(Dispatchers.Main){
                        binding.pgEnergyLevel.secondaryProgress = energyStatus
                        if (lastEnergyLevel == 100) {
                            binding.tvEnergyLevel.text = "Full Energy Charged"
                        } else {
                            binding.tvEnergyLevel.text = "$energyStatus % "
                        }
                    }
                }
            }
        }
    }
     fun loadMilesProgress(subscriptionMilesLeft: String?) {
        var subscriptionMilesLeft : Int? = subscriptionMilesLeft?.toInt()
        CoroutineScope(Dispatchers.Default).launch {
            if (subscriptionMilesLeft != null) {
                for (milesStatus in 1..subscriptionMilesLeft) {
                        delay(1)
                    withContext(Dispatchers.Main){
                        binding.pgMilesLeft.secondaryProgress = subscriptionMilesLeft

                        if (subscriptionMilesLeft == 1000) {
                            binding.tvMilesLeft.text = "Miles Exceeded for the Month"
                        } else {
                            binding.tvMilesLeft.text = "$milesStatus"
                        }
                    }
                }
            }
        }
    }
}