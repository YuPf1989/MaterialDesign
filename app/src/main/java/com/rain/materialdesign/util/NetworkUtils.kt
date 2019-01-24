package com.rain.materialdesign.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Author:rain
 * Date:2018/9/26 10:53
 * Description:
 */
object NetworkUtils {
    /**
     * @return 是否有活动的网络连接
     */
    fun hasNetWorkConnection(context: Context): Boolean {
        //获取连接活动管理器
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //获取链接网络信息
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isAvailable

    }

    /**
     * @return 返回boolean ,是否为wifi网络
     */
    fun hasWifiConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        //是否有网络并且已经连接
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    /**
     * @return 返回boolean, 判断网络是否可用, 是否为移动网络
     */

    fun hasGPRSConnection(context: Context): Boolean {
        //获取活动连接管理器
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return networkInfo != null && networkInfo.isAvailable

    }

    /**
     * @return 判断网络是否可用，并返回网络类型，ConnectivityManager.TYPE_WIFI，ConnectivityManager.TYPE_MOBILE，不可用返回-1
     */
    fun getNetWorkConnectionType(context: Context): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        return if (wifiNetworkInfo != null && wifiNetworkInfo.isAvailable) {
            ConnectivityManager.TYPE_WIFI
        } else if (mobileNetworkInfo != null && mobileNetworkInfo.isAvailable) {
            ConnectivityManager.TYPE_MOBILE
        } else {
            -1
        }
    }
}
