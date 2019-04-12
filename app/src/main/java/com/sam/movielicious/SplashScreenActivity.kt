package com.sam.movielicious

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.sam.movielicious.ui.movies.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        av_from_code.setAnimation("loader.json")
        av_from_code.playAnimation()
        av_from_code.loop(true)

        Handler().postDelayed({
            goToMain()

        }, 3000)
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}