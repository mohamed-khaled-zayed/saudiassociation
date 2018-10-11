package com.binarycase.saudiassociation.ui.screens.splashScreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.binarycase.saudiassociation.R
import com.binarycase.saudiassociation.loginRegister.data.CacheUtils
import com.binarycase.saudiassociation.loginRegister.ui.activity.SignInActivity
import com.binarycase.saudiassociation.ui.screens.mainScreen.MainActivity
import com.binarycase.saudiassociation.utils.DeviceUtils
import com.blankj.utilcode.util.PhoneUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    setContentView(R.layout.activity_splash)
    DeviceUtils.init(this)
//    Glide.with(this@SplashActivity).asGif().load(R.drawable.splash_gif).into(splashScreen)
    Handler().postDelayed({
      if (CacheUtils.checkUserState(this,"isLogin").equals("true")){
        startActivity(Intent(this@SplashActivity,MainActivity::class.java))
        finish()
      }else{
        startActivity(Intent(this@SplashActivity,SignInActivity::class.java))
        finish()
      }
    },3000)
  }
}
