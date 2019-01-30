package com.rain.materialdesign.base

import android.os.Bundle
import com.cxz.swipelibrary.SwipeBackActivityBase
import com.cxz.swipelibrary.SwipeBackActivityHelper
import com.cxz.swipelibrary.SwipeBackLayout
import com.cxz.swipelibrary.Utils

/**
 * Author:rain
 * Date:2019/1/28 15:34
 * Description:
 */
abstract class BaseSwipeBackActivity:BaseActivity(),SwipeBackActivityBase {
    private lateinit var mHelper: SwipeBackActivityHelper

    /**
     * SwipeBack Enable
     */
    open fun enableSwipeBack(): Boolean = true

    /**
     * 初始化 SwipeBack
     */
    private fun initSwipeBack() {
        // 重写此方法是否开启swipeback
        setSwipeBackEnable(enableSwipeBack())
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout.setEnableGesture(enable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = SwipeBackActivityHelper(this)
        mHelper.onActivityCreate()
        initSwipeBack()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }

    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mHelper.swipeBackLayout
    }

    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }
}