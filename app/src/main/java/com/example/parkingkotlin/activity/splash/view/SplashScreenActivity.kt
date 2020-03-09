package com.example.parkingkotlin.activity.splash.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.parkingkotlin.R
import com.example.parkingkotlin.activity.main.view.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            Animatoo.animateFade(this)
            finish()
        }, SPLASH_TIME_OUT)
    }
}
