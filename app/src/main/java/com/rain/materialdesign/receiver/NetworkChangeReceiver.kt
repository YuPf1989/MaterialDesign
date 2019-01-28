package com.rain.materialdesign.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rain.materialdesign.event.NetworkChangeEvent
import com.rain.materialdesign.util.Constant
import com.rain.materialdesign.util.NetWorkUtil
import com.rain.materialdesign.util.Preference
import org.greenrobot.eventbus.EventBus

/**
 * 个人认为需要慎重使用网络状态发生变化的广播
 */
class NetworkChangeReceiver : BroadcastReceiver() {

    /**
     * 缓存上一次的网络状态
     * todo 不清楚存储该值意义在哪里
     */
    private var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetWorkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
    }

}