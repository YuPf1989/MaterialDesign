package com.rain.materialdesign.util

import com.franmontiel.persistentcookiejar.PersistentCookieJar

/**
 * Author:rain
 * Date:2019/1/21 17:12
 * Description:
 */
object Constant {
    const val LANGUAGE_TYPE = "language_type"

    const val LANGUAGE_TYPE_ENGLISH = 1

    const val LANGUAGE_TYPE_CHINESE = 2

    /**
     * cookie持久化
     */
    var cookiejar: PersistentCookieJar? = null


    const val BASE_URL = "http://www.wanandroid.com/"

    const val HAS_NETWORK_KEY = "has_network"

    const val LOGIN_KEY = "login"
}