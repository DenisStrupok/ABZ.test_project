package com.abztest.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData

class NetworkConnection(private val context: Context) : LiveData<Boolean>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val connectionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            postValue(isConnected())
        }

    }

    override fun onActive() {
        super.onActive()
        context.registerReceiver(
            connectionReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        postValue(isConnected())
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(connectionReceiver)
    }

    fun isConnected(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}