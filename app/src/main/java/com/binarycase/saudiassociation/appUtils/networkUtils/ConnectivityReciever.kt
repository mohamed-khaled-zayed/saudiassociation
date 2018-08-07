package com.binarycase.saudiassociation.appUtils.networkUtils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectivityReciever :BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    val connectivityManager = context?.getSystemService(
        Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    networkStatus(activeNetwork)
  }

  private fun networkStatus(activeNetwork: NetworkInfo?) {
    if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting) {
      NetworkUtils.getNetworkUtils().setNetworkStatus(NetworkUtils.DISCONNECTED)
    } else if (activeNetwork.state == NetworkInfo.State.CONNECTING) {
      NetworkUtils.getNetworkUtils().setNetworkStatus(NetworkUtils.CONNECTING)
    } else if (activeNetwork.isConnected) {
      NetworkUtils.getNetworkUtils().setNetworkStatus(NetworkUtils.CONNECTED)
    }
  }
}