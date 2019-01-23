package com.rain.materialdesign.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.afollestad.materialdialogs.color.CircleView
import com.rain.materialdesign.R
import com.rain.materialdesign.event.NetworkChangeEvent
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
     * theme color
     */
    protected var mThemeColor: Int = SettingUtil.getColor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initView(savedInstanceState)
        start()
    }

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
    abstract fun getLayoutId(): Int


    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
//        hasNetwork = event.isConnected
//        checkNetwork(event.isConnected)
    }

    protected fun initToolbar(toobar: Toolbar, title:String, homeAsUpEnabled:Boolean) {
        toobar.title = title
        setSupportActionBar(toobar)
        // 设置toolbar是否有返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
        toobar.setNavigationOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        initThemeColor()
    }

    /**
     * 初始化主题颜色
     */
    open fun initThemeColor() {
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
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }
}