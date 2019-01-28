package com.rain.materialdesign.ui.fragment

import android.view.View
import com.rain.materialdesign.R
import com.rain.materialdesign.base.BaseFragment

/**
 * Author:rain
 * Date:2019/1/28 11:32
 * Description:
 */
class NavigationFragment: BaseFragment() {

    companion object {
        fun getInstance() = NavigationFragment()
    }
    override fun initView(view: View) {
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_test
    }

    override fun lazyLoad() {
    }
}