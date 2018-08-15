package com.binarycase.saudiassociation.utils

import android.content.Context
import android.provider.Settings.Secure

open class DeviceUtils {
  var deviceToken =""

  private constructor()
  companion object {
    private var instance :DeviceUtils?=null
    fun init (context: Context){
        instance = DeviceUtils()
        instance!!.deviceToken  = Secure.getString(context.contentResolver,Secure.ANDROID_ID)

    }
    fun getInstance() :DeviceUtils? {
      return instance
    }
  }
}