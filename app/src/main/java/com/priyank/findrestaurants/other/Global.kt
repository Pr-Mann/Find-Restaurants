package com.priyank.findrestaurants.other

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Global {
    companion object {
        fun checkNet(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
    }
}