package com.example.tokokompalfan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.tokokompalfan.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val iv_splash = binding.ivSplash
        val tv_title = binding.tvTitle

        val fadein = AnimationUtils.loadAnimation(this,R.anim.fade_in_animation)

        iv_splash.startAnimation(fadein)
        tv_title.startAnimation(fadein)

        Handler().postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500.toLong())
    }
}