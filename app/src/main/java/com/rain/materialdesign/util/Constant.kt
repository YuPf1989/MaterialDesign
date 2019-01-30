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
    const val USERNAME_KEY = "username"
    const val PASSWORD_KEY = "password"
    const val TOKEN_KEY = "token"

    const val TYPE_KEY = "type"

    object Type {
        const val COLLECT_TYPE_KEY = "collect_type"
        const val ABOUT_US_TYPE_KEY = "about_us_type_key"
        const val SETTING_TYPE_KEY = "setting_type_key"
        const val SEARCH_TYPE_KEY = "search_type_key"
        const val ADD_TODO_TYPE_KEY = "add_todo_type_key"
        const val SEE_TODO_TYPE_KEY = "see_todo_type_key"
        const val EDIT_TODO_TYPE_KEY = "edit_todo_type_key"
    }
}