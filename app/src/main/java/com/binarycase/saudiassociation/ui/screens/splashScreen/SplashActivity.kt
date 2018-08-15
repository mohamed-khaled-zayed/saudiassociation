package com.binarycase.saudiassociation.ui.screens.splashScreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.binarycase.saudiassociation.R
import com.binarycase.saudiassociation.ui.screens.mainScreen.MainActivity
import com.binarycase.saudiassociation.utils.DeviceUtils

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    setContentView(R.layout.activity_splash)
    DeviceUtils.init(this)
    Handler().postDelayed({
      startActivity(Intent(this@SplashActivity,MainActivity::class.java))
      finish()
    },1000)
  }
}
