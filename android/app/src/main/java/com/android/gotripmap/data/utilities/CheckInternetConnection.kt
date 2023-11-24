package com.android.gotripmap.data.utilities

import android.content.Context
import android.net.ConnectivityManager

class CheckInternetConnection {
  fun invoke(context: Context): Boolean {
    val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)
    return networkCapabilities != null
  }
}
