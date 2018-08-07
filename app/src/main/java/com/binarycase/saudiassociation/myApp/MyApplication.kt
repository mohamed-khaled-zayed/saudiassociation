package com.binarycase.saudiassociation.myApp

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.binarycase.saudiassociation.di.components.ApplicationComponent
import com.binarycase.saudiassociation.di.components.NetworkComponent
import com.binarycase.saudiassociation.appUtils.Constants
import com.binarycase.saudiassociation.appUtils.networkUtils.ConnectivityReciever
import com.binarycase.saudiassociation.di.components.DaggerApplicationComponent

class MyApplication : Application(){
private lateinit var appComponent: ApplicationComponent
lateinit var networkComponent: NetworkComponent
  private lateinit var networkDetector: ConnectivityReciever


  override fun onCreate() {
  super.onCreate()
  appComponent = DaggerApplicationComponent.builder()
      .applicationContext(this)
      .builder()

//  networkComponent = DaggerNetworkComponent.builder()
//      .application(this)
//      .baseUrl(Constants.BASE_URL)
//      .builder()

  networkDetector = ConnectivityReciever()
  val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
  registerReceiver(networkDetector, intent)

//  StatesConfigFactory.intialize().initDefaultViews()
}
  override fun onTerminate() {
    super.onTerminate()
    networkDetector.abortBroadcast()
  }
}