package com.binarycase.saudiassociation.myApp

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.multidex.MultiDexApplication
import cn.jzvd.JZVideoPlayer
import com.binarycase.saudiassociation.R
import com.binarycase.saudiassociation.di.components.ApplicationComponent
import com.binarycase.saudiassociation.di.components.NetworkComponent
import com.binarycase.saudiassociation.utils.Constants
import com.binarycase.saudiassociation.utils.networkUtils.ConnectivityReciever
import com.binarycase.saudiassociation.di.components.DaggerApplicationComponent
import com.binarycase.saudiassociation.di.components.DaggerNetworkComponent
import com.tripl3dev.prettystates.StatesConfigFactory
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class MyApplication : MultiDexApplication() {
  private lateinit var appComponent: ApplicationComponent
  lateinit var networkComponent: NetworkComponent
  private lateinit var networkDetector: ConnectivityReciever
companion object {
  const val FAVOURITE_LOADING=3333
  const val WORDS_SEARCH=3334
}
  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerApplicationComponent.builder()
        .applicationContext(this)
        .builder()

    networkComponent = DaggerNetworkComponent.builder()
        .application(this)
        .baseUrl(Constants.BASE_URL)
        .builder()

    CalligraphyConfig.initDefault(
        CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/Cairo-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    )

    networkDetector = ConnectivityReciever()
    val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    registerReceiver(networkDetector, intent)

    StatesConfigFactory.intialize()
        .initDefaultViews()
    StatesConfigFactory.get()
        .setDefaultEmptyLayout(R.layout.state_view_empty)
        .setDefaultErrorLayout(R.layout.state_view_error)
        .addStateView(WORDS_SEARCH,R.layout.stateview_search_frag_holder)
    JZVideoPlayer.WIFI_TIP_DIALOG_SHOWED = true
  }

  override fun onTerminate() {
    super.onTerminate()
    networkDetector.abortBroadcast()
  }
}