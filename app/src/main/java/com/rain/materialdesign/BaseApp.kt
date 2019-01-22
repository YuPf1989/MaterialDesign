package com.rain.materialdesign

import android.app.Application

/**
 * Author:rain
 * Date:2019/1/18 16:12
 * Description:
 */
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        INSTANCE = this

    }

    companion object {
        lateinit var INSTANCE: BaseApp
            private set
    }
}