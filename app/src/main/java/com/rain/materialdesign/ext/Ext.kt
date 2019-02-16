package com.rain.materialdesign.ext

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.rain.materialdesign.App
import com.rain.materialdesign.R
import com.rain.materialdesign.util.Constant
import com.rain.materialdesign.util.ToastUtil
import java.text.SimpleDateFormat
import java.util.*

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

fun Any.loge(content: String?) {
    loge(this.javaClass.simpleName, content ?: "")
}

/**
 * 获取cookieJar单例对象
 */
fun getCookieJar(): PersistentCookieJar {
    return Constant.cookiejar
        ?: PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.INSTANCE)).apply {
            Constant.cookiejar = this
        }
}

/**
 * 清除cookie
 */
fun clearCookies() {
    Constant.cookiejar?.clear()
}

fun Fragment.showToast(content: String) {
    ToastUtil.showToast(content)
}

fun Context.showToast(content: String) {
    ToastUtil.showToast(content)
}

fun Activity.showSnackMsg(msg: String) {
    val snackbar = Snackbar.make(this.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this, R.color.White))
    snackbar.show()
}

fun Fragment.showSnackMsg(msg: String) {
    this.activity ?: return
    val snackbar = Snackbar.make(this.activity!!.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this.activity!!, R.color.White))
    snackbar.show()
}


/**
 * getAgentWeb
 */
fun String.getAgentWeb(
    activity: Activity,
    webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams,
    webView: WebView,
    webChromeClient: WebChromeClient?,
    webViewClient: WebViewClient
) = AgentWeb.with(activity)//传入Activity or Fragment
    .setAgentWebParent(webContent, 1, layoutParams)//传入AgentWeb 的父控件
    .useDefaultIndicator()// 使用默认进度条
    .setWebView(webView)
    .setWebChromeClient(webChromeClient)
    .setWebViewClient(webViewClient)
    .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
    .createAgentWeb()//
    .ready()
    .go(this)

/**
 * 格式化当前日期
 */
fun formatCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(Date())
}







