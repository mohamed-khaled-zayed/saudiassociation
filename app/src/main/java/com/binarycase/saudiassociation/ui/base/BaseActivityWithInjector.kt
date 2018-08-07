package com.binarycase.saudiassociation.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.binarycase.saudiassociation.appUtils.networkUtils.NetworkUtils
import com.binarycase.saudiassociation.myApp.MyApplication
import com.binarycase.saudiassociation.di.modules.ViewModelFactory
import javax.inject.Inject

 abstract class BaseActivityWithInjector  : AppCompatActivity() , NetworkUtils.ConnectionStatusCB{
  @Inject
  lateinit var factory: ViewModelFactory

  lateinit var vm: ViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (application as MyApplication).networkComponent.inject(this)
    vm = ViewModelProviders.of(this, factory)[getActivityVM()]
    onConnectionChanged()

  }
   abstract fun getActivityVM(): Class<out ViewModel>


   override fun onConnected() {
   }

   override fun onDisconnected() {
   }

   fun onConnectionChanged() {
     NetworkUtils.getNetworkUtils().getNetworkStatus().subscribe {
       when (it) {
         NetworkUtils.CONNECTED -> {
           onConnected()
         }
         NetworkUtils.DISCONNECTED -> {
           onDisconnected()
         }
       }
     }
   }

 }