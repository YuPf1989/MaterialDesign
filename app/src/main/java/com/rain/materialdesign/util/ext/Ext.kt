package com.rain.materialdesign.util.ext

import android.util.Log

/**
 * Author:rain
 * Date:2019/1/8 14:51
 * Description:
 */

/**
 * Log.e在kotlin语法上的扩展,[tag]tag,[content]输出内容
 */
fun loge(tag: String, content: String?) {

    Log.e(tag, content ?: tag)
}

