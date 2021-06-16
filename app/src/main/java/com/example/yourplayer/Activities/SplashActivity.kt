package com.example.yourplayer.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import com.example.yourplayer.R
import com.example.yourplayer.ViewModels.PlaylistViewModel
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    val SPLASH_TIME = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        logo.animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        logo.animation.duration = 1500

        app_name.animation = AnimationUtils.loadAnimation(this, R.anim.from_bottom)
        app_name.animation.duration = 2000


        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }, SPLASH_TIME.toLong())
    }


}