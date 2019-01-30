package com.rain.materialdesign.base

import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.afollestad.materialdialogs.color.CircleView
import com.cxz.multiplestatusview.MultipleStatusView
import com.rain.materialdesign.R
import com.rain.materialdesign.event.NetworkChangeEvent
import com.rain.materialdesign.receiver.NetworkChangeReceiver
import com.rain.materialdesign.util.Constant
import com.rain.materialdesign.util.Preference
import com.rain.materialdesign.util.StatusBarUtil
import com.rain.materialdesign.widget.SettingUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Author:rain
 * Date:2018/11/16 9:58
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName

    /**
     * check login
     */
    protected var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    /**
     * theme color
     */
    protected var mThemeColor: Int = SettingUtil.getColor()

    /**
     * 网络状态变化的广播
     */
    protected var mNetworkChangeReceiver: NetworkChangeReceiver? = null

    /**
     * 缓存上一次的网络状态
     */
    protected var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true

    /**
     * 开始请求
     */
    abstract fun start()

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 设置布局id
     */
    abstract fun attachLayoutRes(): Int

    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        hasNetwork = event.isConnected
        checkNetwork(event.isConnected)
    }

    private fun checkNetwork(isConnected: Boolean) {
        if (isConnected) {
            doReConnected()
        }
    }

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(attachLayoutRes())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initView(savedInstanceState)
        start()
        initListener()
    }

    private fun initListener() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    protected fun initToolbar(toobar: Toolbar, title: String, homeAsUpEnabled: Boolean) {
        toobar.title = title
        setSupportActionBar(toobar)
        // 设置toolbar是否有返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
        toobar.setNavigationOnClickListener { finish() }
    }

    override fun onResume() {
        // 动态注册网络变化广播
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(mNetworkChangeReceiver, filter)
        super.onResume()
        initColor()
    }

    /**
     * 初始化主题颜色
     */
    open fun initColor() {
        mThemeColor = if (!SettingUtil.getIsNightMode()) {
            SettingUtil.getColor()
        } else {
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarUtil.setColor(this, mThemeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.statusBarColor = CircleView.shiftColorDown(mThemeColor)
//            // 最近任务栏上色
//            val tDesc = ActivityManager.TaskDescription(
//                    getString(R.string.app_name),
//                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
//                    mThemeColor)
//            setTaskDescription(tDesc)
            if (SettingUtil.getNavBar()) {
                window.navigationBarColor = CircleView.shiftColorDown(mThemeColor)
            } else {
                window.navigationBarColor = Color.BLACK
            }
        }
    }

    override fun onPause() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver)
            mNetworkChangeReceiver = null
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }
}