package com.example.todoapp.modules

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityOngoingscreenBinding

class OnboardingScreen01 : AppCompatActivity() {
    private lateinit var binding : ActivityOngoingscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOngoingscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getStarted.setOnClickListener {

            if (!isUserLoggedInOnce()) {
                setUserLoggedInOnce()
            }

         //   val intent = Intent(this, HomeScreen::class.java)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
         //   startActivity(intent)
        }

    }

    private fun isUserLoggedInOnce(): Boolean {
        val sharedPreferences = getSharedPreferences("taskPivotPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("userLoggedInOnce", false)
    }

    private fun setUserLoggedInOnce() {
        val sharedPreferences = getSharedPreferences("taskPivotPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("userLoggedInOnce", true)
        editor.apply()
    }
}